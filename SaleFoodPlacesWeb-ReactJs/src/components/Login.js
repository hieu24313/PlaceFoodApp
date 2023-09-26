import { useContext, useEffect, useRef, useState } from "react";
import { MyUserContext } from "../App";
import { Alert, Button, Form } from "react-bootstrap";
import Apis, { authApi, endpoints } from "../configs/Apis";
import cookie from "react-cookies";
import { Link, Navigate, useSearchParams } from "react-router-dom";
import MySpinner from "../layout/MySpinner";
import '../resources/css/Login.css';
import jwt_decode from "jwt-decode";
import { GoogleLogin, GoogleOAuthProvider } from "@react-oauth/google";
import { MDBBtn, MDBCard, MDBCardBody, MDBCol, MDBContainer, MDBIcon, MDBInput, MDBRow } from "mdb-react-ui-kit";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ReCAPTCHA from "react-google-recaptcha";


const Login = () => {

    const [loading, setLoading] = useState(false);
    const [user, dispatch] = useContext(MyUserContext);
    const [username, setUsername] = useState();
    const [password, setPassword] = useState("");
    const [q] = useSearchParams();
    const [err, setErr] = useState(null);
    const notify = (x) => toast(x);
    const avatar = useRef();
    const [kt, setKt] = useState(false);
    const [apiLoginGooleKey, setApiLoginGooleKey] = useState(null)
    const [apiRecaptchaKey, setApiRecaptchaKey] = useState(null);

    useEffect(() => {
        const getKey = async () => {
            try {
                let { data } = await Apis.get(endpoints['get-login-google-key']);
                setApiLoginGooleKey(data);
                console.log(data);

                let res = await Apis.get(endpoints['get-recaptcha-key']);
                setApiRecaptchaKey(res.data);
                console.log(res.data);
            } catch (err) {
                console.log(err)
            }

        }
        getKey();
    }, [])

    const login = (evt) => {
        evt.preventDefault();
        setLoading(true);
        if (kt === true) {
            const process = async () => {
                try {
                    setLoading(true);
                    //lấy token login
                    let res = await Apis.post(endpoints['login'], {
                        "username": username.trim(),
                        "password": password
                    });

                    //cookie khác của thầy, xem lại nếu lỗi.....:)))))) -------------đã fix
                    cookie.save("token", res.data);    //lưu cái res.data kia bằng biến token vào cookie 

                    let { data } = await authApi().get(endpoints['current-user']);
                    cookie.save("user", data); //lưu cái data kia bằng biến user vào cookie 

                    dispatch({
                        "type": "login",
                        "payload": data
                    });

                } catch (ex) {
                    setLoading(false);
                    notify(ex.request.responseText);


                }
            }

            process();
        }
        else {
            notify("Vui lòng xác minh bạn là con người!");
            setLoading(false);
        }


    }

    const loginGoogle = async (decoded) => {
        if (kt === true) {
            console.log(decoded)
            let form = new FormData();
            form.append("avatar", new Blob());
            form.append("pathAvatarGoogle", decoded.picture);
            form.append("username", decoded.email);
            form.append("firstname", decoded.family_name);
            form.append("lastname", decoded.given_name);
            // form.append("phonenumber","");
            // form.append("location", "");
            form.append("email", decoded.email);

            try {
                let res = await Apis.post(endpoints["login-google"], form);
                cookie.save("token", res.data);    //lưu cái res.data kia bằng biến token vào cookie 

                let { data } = await authApi().get(endpoints['current-user']);
                cookie.save("user", data); //lưu cái data kia bằng biến user vào cookie 

                dispatch({
                    "type": "login",
                    "payload": data
                });
            } catch (err) {
                notify(err.request.responseText)
            }
        } else {
            notify("Vui lòng xác minh bạn là con người!");
        }

    }

    const checkRecaptcha = () => {
        setKt(true);
    }


    if (user !== null) {
        let next = q.get("next") || "/";
        return <Navigate to={next} />;

    }
    return <>
        <Form onSubmit={login}>
            <div className="">
                {/* {err !== null ? <Alert className="alert-danger">{err}</Alert> : ""} */}
                {/* <div>
                    <button onClick={notify}>Notify!</button>
                    <ToastContainer />
                </div> */}
                {/* <h1 className="text-center text-info">Đăng Nhập</h1> */}

                <MDBContainer fluid >

                    <MDBRow className='d-flex justify-content-center align-items-center h-100'>
                        <MDBCol col='12' className="mdb_login_form">

                            <MDBCard className='bg-dark text-white my-5 mx-auto' style={{ borderRadius: '1rem', maxWidth: '400px' }}>
                                <MDBCardBody className='p-5 d-flex flex-column align-items-center mx-auto w-100'>

                                    <h2 className="fw-bold mb-2 text-uppercase">Đăng nhập</h2>
                                    <p className="text-white-50 mb-5">Hãy nhập tài khoản và mật khẩu của bạn</p>

                                    <MDBInput wrapperClass='mb-4 mx-5 w-100' value={username} onChange={e => setUsername(e.target.value)} labelClass='text-white' label='Tài Khoản' id='formControlLg' type='text' size="lg" />
                                    <MDBInput wrapperClass='mb-4 mx-5 w-100' value={password} onChange={e => setPassword(e.target.value)} labelClass='text-white' label='Mật Khẩu' id='formControlLg' type='password' size="lg" />
                                    {apiRecaptchaKey === null ? <MySpinner /> : <ReCAPTCHA
                                        sitekey={apiRecaptchaKey}
                                        onChange={checkRecaptcha}
                                    />}

                                    <p className="small mb-3 pb-lg-2"><Link class="text-white-50" to="/changPassword">Quên mật khẩu?</Link></p>

                                    {loading === true ? <MySpinner /> : <MDBBtn outline className='mx-2 px-5' type="submit" color='white' size='lg'>Đăng Nhập</MDBBtn>}
                                    <ToastContainer />

                                    <div className='d-flex flex-row mt-3 mb-5'>
                                        {apiLoginGooleKey === null ? "" :
                                            <>
                                                <GoogleOAuthProvider clientId={apiLoginGooleKey}>
                                                    <GoogleLogin
                                                        className="login_google"
                                                        clientId={apiLoginGooleKey}
                                                        onSuccess={(credentialResponse) => {
                                                            // console.log("Đăng nhập thành công", credentialResponse.credential);
                                                            var token = credentialResponse.credential;
                                                            var decoded = jwt_decode(token);
                                                            console.log(decoded);
                                                            loginGoogle(decoded);

                                                        }}
                                                        onFailure={(error) => {
                                                            notify("Đăng nhập không thành công");
                                                        }}
                                                        redirectUri="http://localhost:3000"
                                                    />
                                                </GoogleOAuthProvider>
                                            </>
                                        }

                                    </div>

                                    <div>
                                        <p className="mb-0">Bạn chưa có tài khoản? <Link class="text-white-50 fw-bold" to="/register">Đăng ký tại đây</Link></p>

                                    </div>
                                </MDBCardBody>
                            </MDBCard>

                        </MDBCol>
                    </MDBRow>

                </MDBContainer>
            </div>
        </Form>


    </>
}
export default Login;