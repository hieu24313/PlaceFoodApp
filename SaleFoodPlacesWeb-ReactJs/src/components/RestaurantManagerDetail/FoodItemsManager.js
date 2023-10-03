import { Link, useParams } from "react-router-dom";
import RestaurantManagerConpoment from "../../layout/RestaurantManagerComponent"
import { MDBBtn, MDBCard, MDBCardBody, MDBInput } from "mdb-react-ui-kit";
import { Button, Card, Col, Form, Row } from "react-bootstrap";
import MySpinner from "../../layout/MySpinner";
import { useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/Apis";

const FoodItemManager = () => {
    const { restaurantId } = useParams();
    const [foodItems, setFoodItems] = useState([]);
    const [uniFood, setUniFood] = useState();
    const [loading, setLoading] = useState(false);
    const [page, setPage] = useState('index');
    //index
    //new
    //update

    useEffect(() => {
        const loadFoodItems = async () => {
            try {
                let e = `${endpoints['fooditems']}?restaurantId=${restaurantId}`;

                let res = await authApi().get(e);

                setFoodItems(res.data);
                console.log(res.data)
            } catch (ex) {
                console.error(ex);
            }
        }
        loadFoodItems();
    }, [restaurantId])

    const getFoodItem = async (evt, id) => {
        evt.preventDefault();
        setPage('update');
        try {
            let res = await authApi().get(endpoints['detail'](id));
            setUniFood(res.data);
        } catch (e) {
            console.log(e);
        }
    }

    const newFood = (evt) => {
        evt.preventDefault();
        setPage('new');
    }

    const back = (evt) => {
        evt.preventDefault();
        setPage('index');
    }

    return <>
        <div className="dasboard_big">
            <div className="dasboard_1" >
                <RestaurantManagerConpoment />
            </div>
            <div className="dasboard_2">
                {page === 'index' ? <>
                    <h1 className="text-center">Trang quản lý thức ăn {restaurantId}</h1>
                    <div>
                        <Button onClick={newFood} className="btn-success">Thêm Món</Button>
                    </div>
                    <Row>
                        {loading === true ? <MySpinner /> : <>
                            {Object.values(foodItems).map(f => {
                                return <>
                                    <Col xs={12} md={4} >
                                        <Card style={{ width: '18rem' }}>
                                            <Card.Img variant="top" src={f.avatar} />
                                            <Card.Body>
                                                <Card.Title>{f.foodName}</Card.Title>
                                                <Card.Text>
                                                    {f.description}
                                                </Card.Text>
                                                <Card.Text className="text-danger" style={{ fontSize: 25 + "px" }}>
                                                    {f.price} VNĐ
                                                </Card.Text>
                                                <Button onClick={(e) => getFoodItem(e, f.foodId)} variant="primary">Chỉnh sửa</Button>
                                            </Card.Body>
                                        </Card>
                                    </Col>
                                </>
                            })}
                        </>}
                    </Row>
                </> : <>
                            {page === 'update' ? <>
                            <h1 className="text-center">Cặp nhật món ăn</h1>
                            <Button onClick={back}>Quay lại</Button>
                                {/* giao diện update */}
                            </> : <>
                            <h1 className="text-center">Thêm món</h1>
                            <Button onClick={back}>Quay lại</Button>
                                {/* giao diện new mới */}
                            </>}
                </>}

                {/* <Form>
                    <div className='mask gradient-custom-3'></div>
                    <div>
                        <div className='m-5' style={{ maxWidth: '600px', margin: 'auto auto' }}>
                            <MDBCardBody className='px-5'>
                                <MDBInput wrapperClass='mb-4' required label='Tài Khoản' size='lg' id='form1' type='text' />
                                <MDBInput wrapperClass='mb-4' required label='Mật Khẩu' size='lg' id='form3' type='password' />
                                <MDBInput wrapperClass='mb-4' required label='Nhập Lại Mật Khẩu' size='lg' id='form4' type='password' />
                                <MDBInput wrapperClass='mb-4' size='lg' id='form4' type='file' />
                                <div className='d-flex flex-row justify-content-center mb-4'>
                                    <span>Đã có tài khoản?<Link to="/login"> Đăng Nhập</Link></span>
                                </div>
                                <MDBBtn type="submit" className='mb-4 w-100 gradient-custom-4' size='lg'>Đăng Ký</MDBBtn>
                            </MDBCardBody>
                        </div>
                    </div>
                </Form> */}
            </div>
        </div>
    </>
}

export default FoodItemManager;