import { useContext, useEffect, useRef, useState } from "react";
import { Alert, Button, Col, Form, Image, Nav, Row, Table } from "react-bootstrap";
import MySpinner from "../layout/MySpinner";
import Apis, { authApi, endpoints } from "../configs/Apis";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import img from '../resources/img/react_icon.png'
import '../resources/css/RegisterRestaurant.css'
import { MyUserContext } from "../App";
import { MDBBtn, MDBCard, MDBCardBody, MDBInput } from "mdb-react-ui-kit";
import ProfileComponents from "../layout/ProfileComponents";

const RegisterRestaurant = () => {

    const [restaurant, setRestaurant] = useState([]);
    // const [q] = useSearchParams();
    const [user,] = useContext(MyUserContext);


    const loadRestaurant = async () => {
        try {
            let e = `${endpoints['restaurant']}?current_user_UserId=${user.userId}`;

            let res = await Apis.get(e);

            setRestaurant(res.data);
            console.log(res.data)
        } catch (ex) {
            console.error(ex);
        }
    }
    useEffect(() => {
        loadRestaurant();
    }, [])

    const [regi_restaurant, setRegi_Restaurant] = useState({
        "restaurantName": "",
        "location": ""
        // "avatar": ""
    });
    const avatar = useRef();
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();


    const register_restaurant = (evt) => {
        evt.preventDefault();
        setLoading(true);
        const process = async () => {
            let form = new FormData();

            form.append("restaurantName", regi_restaurant["restaurantName"]);
            form.append("location", regi_restaurant["location"]);



            form.append("avatar", avatar.current.files[0]);
            // console.log(form)
            let res = await authApi().post(endpoints['register-restaurant'], form);

            if (res.status === 201) {
                loadRestaurant()
                setLoading(false);
                // nav("/");
            }
        }
        process();

    }

    const change = (evt, field) => {
        // setUser({...user, [field]: evt.target.value})
        setRegi_Restaurant(current => {
            return { ...current, [field]: evt.target.value }
        })
    }

    if (user === null) {
        return <>
            <Alert className="alert-danger">Vui lòng <Link to="/login">đăng nhập</Link></Alert>
        </>
    }

    return <>
        <h1 className="text-center text-info">Đăng Ký Nhà Hàng</h1>
        <div className="contain_info ">
        <ProfileComponents />
            <div className="contain_info_2">

                <MDBCard className='m-5 register_form' style={{ maxWidth: '600px' }}>
                    <MDBCardBody className='px-5 register_form_child'>
                        <h2 className="text-uppercase text-center mb-5">Form Đăng Ký Nhà Hàng</h2>
                        <MDBInput wrapperClass='mb-4' required onChange={(e) => change(e, "restaurantName")} label='Tên Nhà Hàng' size='lg' id='form1' type='text' />
                        <MDBInput wrapperClass='mb-4' required onChange={(e) => change(e, "location")} label='Địa Chỉ' size='lg' id='form3' type='text' />
                        <MDBInput wrapperClass='mb-4' ref={avatar} size='lg' id='form4' type='file' />
                        {loading === true ? <MySpinner /> : <MDBBtn type="submit" className='mb-4 w-100 gradient-custom-4' size='lg'>Gửi Yêu Cầu</MDBBtn>}
                    </MDBCardBody>
                </MDBCard>
                <hr />

                <Col>
                    <Table striped bordered hover>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Tên Nhà Hàng</th>
                                <th>Địa Chỉ</th>
                                <th>Trạng thái</th>
                                <th>Chủ nhà hàng</th>
                            </tr>
                        </thead>
                        <tbody>
                            {restaurant == null ? <MySpinner /> : Object.values(restaurant).map(r => {
                                return <tr>
                                    <td>{r.restaurantId}</td>
                                    <td>{r.restaurantName}</td>
                                    <td>{r.location}</td>
                                    <td>{r.confirmationStatus === true ? "Đã Xác Thực" : "Chưa được Xác Thực"}</td>
                                    <td>{r.userId.userId}</td>
                                </tr>
                            })}
                            {restaurant.length === 0 ?<tr><Alert>Bạn chưa đăng ký nhà hàng nào!</Alert></tr> : null}
                        </tbody>
                    </Table>

                </Col>
            </div>

        </div>


    </>
}
export default RegisterRestaurant;