import { useContext, useRef, useState } from "react";
import { Alert, Button, Form } from "react-bootstrap";
import MySpinner from "../layout/MySpinner";
import Apis, { endpoints } from "../configs/Apis";
import { Link, Navigate, useNavigate } from "react-router-dom";
import { MyUserContext } from "../App";
import { MDBBtn, MDBCard, MDBCardBody, MDBCheckbox, MDBContainer, MDBInput } from "mdb-react-ui-kit";
import '../resources/css/Register.css';

const Register = () => {

    const [loading, setLoading] = useState(false);
    const [current_user,] = useContext(MyUserContext);
    const avatar = useRef();
    const nav = useNavigate();
    const [err, setErr] = useState(null);
    const [user, setUser] = useState({
        "username": "",
        "password": "",
        // "firstname": "",
        // "lastname": "",
        // "email": "",
        // "phonenumber": "",
        "confirmPass": "",
        "avatar": ""
    });

    if (current_user !== null) {
        // alert("Vui lòng đăng xuất để đăng ký!!!");
        return <Navigate to="/" />
    }
    const change = (evt, field) => {
        // setUser({...user, [field]: evt.target.value})
        setUser(current => {
            return { ...current, [field]: evt.target.value }
        })
    }

    const register = (evt) => {
        evt.preventDefault();

        const process = async () => {
            let form = new FormData();

            for (let field in user)
                if (field !== "confirmPass" || field !== "avatar")
                    form.append(field, user[field]);

            // form.append("avatar", avatar.current.files[0] !== null ? avatar.current.files[0] : form.append("avatar", user["avatar"]));
            // console.log(form)
            if (avatar.current.files[0] !== undefined) {
                form.append("avatar", avatar.current.files[0]);
            } else {
                form.append("avatar", new Blob());
            }
            setLoading(true)
            console.log(user);
            console.log(form);
            let res = await Apis.post(endpoints['register'], form);
            if (res.status === 201) {
                nav("/login");
            } else
                setErr("Hệ thống bị lỗi!");
        }
        if (user.password === user.confirmPass)
            process();
        else {
            setErr("Mật khẩu KHÔNG khớp!");
        }
    }

    return <>
        {/* <h1 className="text-center text-success">Register</h1> */}

        {err === null ? "" : <Alert variant="danger">{err}</Alert>}
        {/* <Form onSubmit={register}> */}
        {/* <Form.Group className="mb-3">
                <Form.Label>Tên</Form.Label>
                <Form.Control type="text" onChange={(e) => change(e, "firstname")} placeholder="Tên" required />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Họ và chữ lót</Form.Label>
                <Form.Control type="text" onChange={(e) => change(e, "lastname")} placeholder="Họ và chữ lót" required />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" onChange={(e) => change(e, "email")} placeholder="Email" />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Điện thoại</Form.Label>
                <Form.Control type="tel" required onChange={(e) => change(e, "phone")} placeholder="Điện thoại" />
            </Form.Group>  */}
        {/* <Form.Group className="mb-3">
                <Form.Label>Tên đăng nhập</Form.Label>
                <Form.Control value={user.username} required onChange={(e) => change(e, "username")} type="text" placeholder="Tên đăng nhập" />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Mật khẩu</Form.Label>
                <Form.Control value={user.password} required onChange={(e) => change(e, "password")} type="password" placeholder="Mật khẩu" />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Xác nhận mật khẩu</Form.Label>
                <Form.Control value={user.confirmPass} required onChange={(e) => change(e, "confirmPass")} type="password" placeholder="Xác nhận mật khẩu" />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Ảnh đại diện</Form.Label>
                <Form.Control type="file" ref={avatar} />
            </Form.Group>
            <Form.Group className="mb-3">
                {loading === true ? <MySpinner /> : <Button variant="info" type="submit">Đăng ký</Button>}

            </Form.Group>
        </Form> */}

        {/* <MDBContainer fluid className='d-flex align-items-center justify-content-center bg-image' style={{backgroundImage: 'url(https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp)'}}> */}
        <Form onSubmit={register}>
            <div className='mask gradient-custom-3'></div>
            <div className="fake_div">
                <MDBCard className='m-5 register_form' style={{ maxWidth: '600px' }}>
                    <MDBCardBody className='px-5 register_form_child'>
                        <h2 className="text-uppercase text-center mb-5">Tạo Tài Khoản</h2>
                        <MDBInput wrapperClass='mb-4' required onChange={(e) => change(e, "username")} label='Tài Khoản' size='lg' id='form1' type='text' />
                        {/* <MDBInput wrapperClass='mb-4' label='Your Email' size='lg' id='form2' type='email' /> */}
                        <MDBInput wrapperClass='mb-4' required onChange={(e) => change(e, "password")} label='Mật Khẩu' size='lg' id='form3' type='password' />
                        <MDBInput wrapperClass='mb-4' required onChange={(e) => change(e, "confirmPass")} label='Nhập Lại Mật Khẩu' size='lg' id='form4' type='password' />
                        <MDBInput wrapperClass='mb-4' ref={avatar} size='lg' id='form4' type='file' />
                        <div className='d-flex flex-row justify-content-center mb-4'>
                            {/* <MDBCheckbox name='flexCheck' id='flexCheckDefault' label='I agree all statements in Terms of service' /> */}
                            <span>Đã có tài khoản?<Link to="/login"> Đăng Nhập</Link></span>
                        </div>
                        {loading === true ? <MySpinner /> : <MDBBtn type="submit" className='mb-4 w-100 gradient-custom-4' size='lg'>Đăng Ký</MDBBtn>}
                    </MDBCardBody>
                </MDBCard>
            </div>
        </Form>
        {/* </MDBContainer> */}
    </>
}
export default Register;