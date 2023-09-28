import { MDBBtn, MDBCard, MDBCardBody, MDBInput } from "mdb-react-ui-kit";
import { useContext, useState } from "react";
import { Col, Form, Nav, Table } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { MyUserContext } from "../App";
import { authApi, endpoints } from "../configs/Apis";
import '../resources/css/ChangePassword.css'
import ProfileComponents from "../layout/ProfileComponents";
import MySpinner from "../layout/MySpinner";
import { ToastContainer, toast } from "react-toastify";

const ChangePassword = () => {

    const [user,] = useContext(MyUserContext);
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();
    const notify = (x) => toast(x);
    const [newUserPassword, setNewUserPassword] = useState({
        "username": user.username,
        "password": "",
        "newPassword": "",
        "confirmNewPassword": ""
    });

    const change = (evt, field) => {
        // setUser({...user, [field]: evt.target.value})
        setNewUserPassword(current => {
            return { ...current, [field]: evt.target.value.trim() }
        })
    }

    const changePassword = async (evt) => {
        evt.preventDefault();
        if (newUserPassword.newPassword === newUserPassword.confirmNewPassword) {
            try {
                let form = new FormData();
                form.append("username",newUserPassword.username)
                form.append("password",newUserPassword.password)
                form.append("newPassword",newUserPassword.newPassword)
                // let e = `${endpoints['changePassword']}?username=${newUserPassword.username}&password=${newUserPassword.password}&newPassword=${newUserPassword.newPassword}`;
                // let e = `${endpoints['changePassword']}`
                let data = await authApi().post(endpoints['changePassword'], form);
                console.log(data);
                toast.success("Đổi mật khẩu thành công!!")
                setTimeout(() => nav("/profile"), 1500);
            }catch(e){
                console.error(e);
                toast(e);
            }
        }
    }


    return <>
        <h1 className="text-center text-info">Đổi Mật Khẩu</h1>
        <div class="change_pass_page">
        <ToastContainer />
            <div className="contain_info">
                <ProfileComponents />
                <div className="contain_info_2">
                    <Form onSubmit={changePassword} className="form_change_password">
                        <MDBCard className='m-5 register_form' style={{ maxWidth: '600px' }}>
                            <MDBCardBody className='px-5 register_form_child'>
                                <h2 className="text-uppercase text-center mb-5">Form Đổi Mật Khẩu</h2>
                                <MDBInput wrapperClass='mb-4' onChange={(e) => change(e, "password")} required label='Mật khẩu hiện tại' size='lg' id='form1' type='text' />
                                <MDBInput wrapperClass='mb-4' onChange={(e) => change(e, "newPassword")} required label='Mật Khẩu Mới' size='lg' id='form3' type='password' />
                                <MDBInput wrapperClass='mb-4' onChange={(e) => change(e, "confirmNewPassword")} required label='Nhập Lại Mật Khẩu Mới' size='lg' id='form31' type='password' />
                                {loading === true ? <MySpinner /> : <MDBBtn type="submit" className='mb-4 w-100 gradient-custom-4' size='lg'>Đổi Mật Khẩu</MDBBtn>}
                            </MDBCardBody>
                        </MDBCard>
                    </Form>

                </div>
            </div>
        </div>



    </>
}
export default ChangePassword;