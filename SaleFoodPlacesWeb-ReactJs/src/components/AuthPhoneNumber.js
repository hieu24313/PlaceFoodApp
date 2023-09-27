import { ToastContainer, toast } from "react-toastify";
import ProfileComponents from "../layout/ProfileComponents";
import { Button, Form } from "react-bootstrap";
import { MDBBtn, MDBCard, MDBCardBody, MDBInput } from "mdb-react-ui-kit";
import { useState } from "react";
import MySpinner from "../layout/MySpinner";
import { useContext } from "react";
import { MyUserContext } from "../App";
import '../resources/css/AuthPhoneNumber.css'
import { authApi, endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import cookie from "react-cookies";


const AuthPhoneNumber = () => {

    const [loading, setLoading] = useState(false);
    const [loading1, setLoading1] = useState(false);
    const [user, dispatch] = useContext(MyUserContext);
    const [otp, setOTP] = useState();
    const notify = (x) => toast(x);
    const nav = useNavigate();

    let countdown = 5; // Thời gian đếm ngược ban đầu (60 giây)
    

    const count = () => {
        const timerElement = document.getElementById('send_otp_btn');
        function updateTimer() {
        
            timerElement.textContent = countdown + "s (Đã Gửi)";
            if (countdown === 0) {
                clearInterval(intervalId); // Dừng đồng hồ khi countdown đạt 0
                // timerElement.textContent = 'Hết giờ!';
                timerElement.disabled = false;
                timerElement.textContent = "Gửi OTP";
                countdown = 5;
            }
            countdown--;
        }
        
        updateTimer(); // Cập nhật lần đầu
        const intervalId = setInterval(updateTimer, 1000);
    
    }

    const sendOTP = async (evt) => {
        evt.preventDefault();
        setLoading(true);

        const timerElement = document.getElementById('send_otp_btn');
        timerElement.disabled = true;
        count();

        try{
            let res = await authApi().post(endpoints['send-otp'],{
                "phonenumber": user.phonenumber
            });
            toast(res.data);
            setLoading(false);
        }catch(e){
            toast(e);
            setLoading(false);
            console.log(e)
        }

    }



    const authOTP = async (evt) => {
        evt.preventDefault();
        setLoading1(true);

        try{
            let res = await authApi().post(endpoints['check-otp'],{
                "phonenumber": user.phonenumber,
                "otp": otp,
                "username": user.username
            })
            // toast("Đã Gửi")
            if(res.status === 200 ){
                toast(res.data)
                try{
                    let { data } = await authApi().get(endpoints['current-user']);
                    cookie.save("user", data); //lưu cái data kia bằng biến user vào cookie 

                    dispatch({
                        "type": "login",
                        "payload": data
                    });

                }catch(e){
                    console.log(e);
                }
                setTimeout(() => nav("/profile"), 2000)
            }
            setLoading1(false);

        }catch(e){
            toast(e);
            console.log(e)
            setLoading1(false);
        }
    }

    return<>
        <h1 className="text-center text-info">Xác thực số điện thoại</h1>
        <div class="change_pass_page">
        <ToastContainer />
            <div className="contain_info">
                <ProfileComponents />
                <div className="contain_info_2">
                    <Form className="form_change_password">
                        <MDBCard className='m-5 register_form' style={{ maxWidth: '600px' }}>
                            <MDBCardBody className='px-5 register_form_child'>
                                <h2 className="text-uppercase text-center mb-5">Form Xác Thực</h2>
                                <div className="div_send_otp">
                                    <MDBInput wrapperClass='mb-4' defaultValue={user.phonenumber}  readOnly label='Số điện thoại' size='lg' id='form1' type='text' />
                                    {user.otp === "1" ? <><Button disabled id="send_otp_btn" >Đã Xác Thực</Button></> : <>{loading === true ? <MySpinner /> : <Button id="send_otp_btn" onClick={sendOTP}>Gửi OTP</Button>}</>}
                                </div>
                                <MDBInput wrapperClass='mb-4' onChange={(e) => setOTP(e.target.value)}  required label='Nhập Mã OTP' size='lg' id='form3' type='text' />
                                {user.otp === "1" ? <><Button className='mb-4 w-100 gradient-custom-4' size='lg' disabled id="send_otp_btn" >Đã Xác Thực</Button></> : <>{loading1 === true ? <MySpinner /> : <MDBBtn onClick={authOTP} type="submit" className='mb-4 w-100 gradient-custom-4' size='lg'>Xác nhận</MDBBtn>}</>}
                                
                            </MDBCardBody>
                        </MDBCard>
                    </Form>

                </div>
            </div>
        </div>
    </>
}
export default AuthPhoneNumber;