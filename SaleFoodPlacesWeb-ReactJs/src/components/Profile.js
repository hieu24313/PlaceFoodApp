import { useContext, useRef, useState } from "react";
import { Button, Col, Container, Form, Image, Nav, Row } from "react-bootstrap";
import cookie from "react-cookies";
// import img from '../resources/img/healthy-lunch-go-packed-lunch-box.jpg';
import '../resources/css/Profile.css';
import MySpinner from "../layout/MySpinner";
import Apis, { authApi, endpoints } from "../configs/Apis";
import { Link, useNavigate } from "react-router-dom";
import { MyUserContext } from "../App";
import ProfileComponents from "../layout/ProfileComponents";
import { MDBBtn, MDBCardBody, MDBInput } from "mdb-react-ui-kit";
import { ToastContainer, toast } from "react-toastify";

const Profile = () => {


    // const [current_user, setCurrent_User] = useState(cookie.load("user") || null);
    const [current_user, dispatch] = useContext(MyUserContext);
    // const [current_user, setCurrent_User] = useContext(MyUserContext)

    const avatar = useRef();
    const [current_avatar, setCurrent_avatar] = useState(current_user.avatar);
    const notify = (x) => toast(x);
    const [user, setUser] = useState({
        "userId": current_user.userId,
        "firstname": "",
        "lastname": "",
        "username": current_user.username,
        "password": current_user.password,
        "location": "",
        "email": "",
        "phonenumber": "",
        "avatar": current_user.avatar
    });
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();
    const regex = /^0\d{9,10}$/;

    const change = (evt, field) => {
        // setUser({...user, [field]: evt.target.value})
        setUser(current => {
            return { ...current, [field]: evt.target.value.trim() }
        })
    }

    const updateAvatar = (avatar) => {
        // console.log(avatar[0]);
        setCurrent_avatar(avatar[0]);
    }
    // console.log(current_user)

    const reloadUser = async () => {
        try {
            // cookie.remove("user");
            let { data } = await authApi().get(endpoints['current-user']);
            cookie.save("user", data); //lưu cái data kia bằng biến user vào cookie
            // setCurrent_User(cookie.load("user"));
            // let { data } = await authApi().get(endpoints['current-user']);
            // cookie.save("user", data); //lưu cái data kia bằng biến user vào cookie 

            dispatch({
                "type": "login",
                "payload": data
            });
        } catch (err) {
            console.log(err);
        }
    }

    const updateUser = (evt) => {
        evt.preventDefault();
        setLoading(true);
        const process = async () => {
            try {
                let form = new FormData();
                // user["location"] = user["location"].trim();
                // let loca = user["location"].
                // setUser("location", loca );

                for (let field in user) {
                    form.append(field, user[field]);
                }


                // form.append("avatar", avatar.current.files[0]);
                if (avatar.current.files[0] !== undefined) {
                    form.append("avatar", avatar.current.files[0]);
                } else {
                    form.append("avatar", new Blob());
                }
                let res = await authApi().post(endpoints['update-user'], form);
                if (res.status === 200) {
                    setLoading(true);
                    reloadUser();
                    setLoading(false);
                    
                    // nav("/");
                    toast.success("Lưu Thành Công!!");
                }

            } catch (err) {
                toast.error(err.request.responseText);
                console.log(err);
                setLoading(false);
            }

        }
        process();

    }
    if (current_user === null) {
        return <>
            <h1>Vui lòng đăng nhập</h1>
        </>
    }

    return <>
        <Form onSubmit={updateUser}>
        <ToastContainer />
            <h1 className="text-center text-info">Your Profile</h1>
            <div className="contain_info">
                <ProfileComponents />
                <div className="contain_info_2">
                    <div className="avatar">
                        <Image src={current_avatar} rounded />
                        <Form.Control className="avatar_input" accept=".jpg, .jpeg, .png, .gif, .bmp" type="file" onChange={(e) => updateAvatar(e.target.files)} ref={avatar} />
                    </div>  
        {/* "firstname": "",
        "lastname": "",
        "username": current_user.username,
        "password": current_user.password,
        "location": "",
        "email": "",
        "phonenumber": "",
        "avatar": current_user.avatar */}
                    <MDBCardBody className='px-5'>
                        <h2 className="text-uppercase text-center mb-5"> </h2>
                        <MDBInput wrapperClass='mb-4' defaultValue={current_user.firstname} required onChange={(e) => change(e, "firstname")} label='Họ' size='lg' id='form3' type='text' />
                        <MDBInput wrapperClass='mb-4' defaultValue={current_user.lastname} required onChange={(e) => change(e, "lastname")} label='Tên' size='lg' id='form3' type='text' />
                        <MDBInput wrapperClass='mb-4' defaultValue={current_user.username} readOnly required onChange={(e) => change(e, "username")} label='Tên Tài Khoản' size='lg' id='form3' type='text' />
                        <MDBInput wrapperClass='mb-4' defaultValue={current_user.location} required onChange={(e) => change(e, "location")} label='Địa Chỉ' size='lg' id='form3' type='text' />
                        <MDBInput wrapperClass='mb-4' defaultValue={current_user.email} required onChange={(e) => change(e, "email")} label='Email' size='lg' id='form3' type='text' />
                        <div className="auth-btn-otp">
                            <MDBInput wrapperClass='mb-4' defaultValue={current_user.phonenumber} required onChange={(e) => change(e, "phonenumber")} label='Số Điện Thoại' size='lg' id='form3' type='text' />
                            {current_user.otp === "0" ? <Link to="/authPhoneNumber" className="btn btnotp">Xác thực</Link> : null}
                        </div>

                        {loading === true ? <MySpinner /> : <MDBBtn type="submit" className='mb-4 w-100 gradient-custom-4' size='lg'>Lưu</MDBBtn>}
                    </MDBCardBody>

                    {/* <div className="info">
                        <hr />
                        <h4>User Info</h4>
                        <br />
                        <Form.Group className="mb-3" controlId="formFirstName">
                            <Form.Label>Your First Name</Form.Label>
                            <Form.Control
                                onChange={(e) => change(e, "firstname")}
                                type="text"
                                defaultValue={current_user.firstname}
                                aria-label="firstname"
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formLastName">
                            <Form.Label>Your Last Name</Form.Label>
                            <Form.Control
                                onChange={(e) => change(e, "lastname")}
                                type="text"
                                defaultValue={current_user.lastname}
                                aria-label="lastname"
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formUsername">
                            <Form.Label>Your Username</Form.Label>
                            <Form.Control
                                type="text"
                                defaultValue={current_user.username}
                                readOnly
                                aria-label="username"
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formLocation">
                            <Form.Label>Your Location</Form.Label>
                            <Form.Control
                                onChange={(e) => change(e, "location")}
                                type="text"
                                defaultValue={current_user.location}
                                aria-label="location"
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Your Email</Form.Label>
                            <Form.Control
                                onChange={(e) => change(e, "email")}
                                type="email"
                                defaultValue={current_user.email}
                                aria-label="email"
                            />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formPhoneNumber">
                            <Form.Label>Your Phone Number</Form.Label>
                            <Form.Control
                                onChange={(e) => change(e, "phonenumber")}
                                type="text"
                                defaultValue={current_user.phonenumber}
                                aria-label="phonenumber"
                            />
                        </Form.Group>


                        <Form.Group className="mb-3" controlId="formRole">
                            <Form.Label>Type User</Form.Label>
                            <Form.Control
                                type="text"
                                defaultValue={current_user.roleId.roleName}
                                aria-label="role"
                                readOnly
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            {loading === true ? <MySpinner /> : <div className="btn_luu"><Button className="text-center" variant="info" type="submit">Lưu</Button></div>}

                        </Form.Group>

                    </div> */}
                </div>
            </div>
        </Form>
    </>
}
export default Profile;
