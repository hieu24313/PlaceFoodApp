import { useContext, useEffect, useState } from "react";
import { MyUserContext } from "../App";
import { Link, Navigate, useNavigate } from "react-router-dom/dist";
import { Form } from "react-bootstrap";
import { MDBBtn, MDBCard, MDBCardBody, MDBInput } from "mdb-react-ui-kit";
import MySpinner from "../layout/MySpinner";
import Apis, { endpoints } from "../configs/Apis";
import { toast } from "react-toastify";
import { ToastContainer } from "react-toastify";
import ReCAPTCHA from "react-google-recaptcha";

const ForgotPassword = () => {

    const [user,] = useContext(MyUserContext);
    const [loading, setLoading] = useState(false);
    const [hasPhoneNumber, setHasPhoneNumber] = useState(false);
    const [phoneNumber, setPhoneNumber] = useState();
    const [otp, setOTP] = useState();
    const [newPassword, setNewPassword] = useState();
    const [confirmNewPassword, setConfirmNewPassword] = useState();
    const nav = useNavigate();
    const [apiRecaptchaKey, setApiRecaptchaKey] = useState(null);
    const [kt, setKt] = useState(false);


    useEffect(() => {
        const getKey = async () => {
            try {
                let res = await Apis.get(endpoints['get-recaptcha-key']);
                setApiRecaptchaKey(res.data);
                console.log(res.data);
            } catch (err) {
                console.log(err)
            }

        }
        getKey();
    }, [])


    const find_PhoneNumber = async (evt) => {
        evt.preventDefault();
        if (kt) {
            setLoading(true);

            try {
                // setHasPhoneNumber(true)
                let form = new FormData();
                // form.append("otp", otp);
                // form.append("newpassword", newPassword);
                form.append("phonenumber", phoneNumber);
                let res = await Apis.post(endpoints['check-phonenumber'], {
                    "phonenumber": phoneNumber
                });
                if (res.status === 200) {
                    setHasPhoneNumber(true);
                    // let p = document.getElementById("phonenumber");
                    // p.defaultValue(phoneNumber);
                    // p.disabled = true;
                    setLoading(false);
                    toast.success(res.data);
                    //     // nav("/login");
                } else {
                    toast.error(res.data);
                }
            } catch (error) {
                setLoading(false);
                toast.error(error.request.responseText);
                console.log(error);
            }
        } else {
            toast("Vui xác minh bạn là con người!!!")
        }

    }

    const changPassword = async (evt) => {
        if (newPassword === confirmNewPassword) {

            evt.preventDefault();
            setLoading(true);
            let form = new FormData();
            form.append("otp", otp);
            form.append("newpassword", newPassword);
            form.append("phonenumber", phoneNumber);

            try {
                let res = await Apis.post(endpoints['check-otp-and-set-password'], {
                    "phonenumber": phoneNumber,
                    "otp": otp,
                    "newpassword": newPassword
                });
                if (res.status === 200) {
                    toast.success(res.data);
                    setLoading(false);
                    setTimeout(() => nav('/login'), 1000);
                }

            } catch (e) {
                toast.error(e.request.responseText);
                setLoading(false);

            }

        }
        else {
            toast.error("Mật Khẩu không khớp!")
        }

    }

    const checkRecaptcha = () => {
        setKt(true);
    }


    if (user !== null) {
        return <Navigate to="/" />;
    }

    return <>
        <h1 className="text-center text-primary"> </h1>
        <ToastContainer />
        <Form>
            <div className='mask gradient-custom-3'></div>
            <div className="fake_div1" style={{ marginTop: 70 + 'px' }} >
                <MDBCard className='m-5 register_form' style={{ maxWidth: '600px' }}>
                    <MDBCardBody className='px-5 register_form_child'>
                        <h2 className="text-uppercase text-center mb-5">Quên Mật Khẩu</h2>
                        {hasPhoneNumber === true ? <>
                            <MDBInput wrapperClass='mb-4' disabled id="phonenumber" defaultValue={phoneNumber} required label='Số điện thoại' size='lg' type='text' />
                            <div>
                                <label className="text-danger" >Tôi đã gửi 1 mã OTP đến số điện thoại của bạn!</label>
                                <MDBInput wrapperClass='mb-4' required onChange={e => setOTP(e.target.value)} label='Mã OTP' size='lg' id='form3' type='text' />
                                <MDBInput wrapperClass='mb-4' required onChange={e => setNewPassword(e.target.value)} label='Mật Khẩu' size='lg' id='form3' type='password' />
                                <MDBInput wrapperClass='mb-4' required onChange={e => setConfirmNewPassword(e.target.value)} label='Nhập Lại Mật Khẩu' size='lg' id='form4' type='password' />
                            </div>
                            <div className='d-flex flex-row justify-content-center mb-4'>
                                {/* <MDBCheckbox name='flexCheck' id='flexCheckDefault' label='I agree all statements in Terms of service' /> */}
                                <span>Đã có tài khoản?<Link to="/login"> Đăng Nhập</Link></span>
                            </div>
                            {loading === true ? <MySpinner /> : <MDBBtn type="submit" onClick={changPassword} className='mb-4 w-100 gradient-custom-4' size='lg'>Đổi Mật Khẩu</MDBBtn>}

                        </> : <>
                            <MDBInput wrapperClass='mb-4' id="phonenumber" onChange={e => setPhoneNumber(e.target.value)} required label='Nhập số điện thoại' size='lg' type='text' />
                            {apiRecaptchaKey === null ? <MySpinner /> : <ReCAPTCHA
                            className="d-flex flex-row justify-content-center mb-4"
                                sitekey={apiRecaptchaKey}
                                onChange={checkRecaptcha}
                            />}
                            <div className='d-flex flex-row justify-content-center mb-4'>
                                {/* <MDBCheckbox name='flexCheck' id='flexCheckDefault' label='I agree all statements in Terms of service' /> */}
                                <span>Đã có tài khoản?<Link to="/login"> Đăng Nhập</Link></span>
                            </div>
                            {loading === true ? <MySpinner /> : <MDBBtn type="submit" onClick={find_PhoneNumber} className='mb-4 w-100 gradient-custom-4' size='lg'>Tìm Toàn Khoản</MDBBtn>}

                        </>}


                    </MDBCardBody>
                </MDBCard>
            </div>
        </Form>
    </>
}

export default ForgotPassword;