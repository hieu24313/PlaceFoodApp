import { Link, useNavigate, useParams } from "react-router-dom";
import RestaurantManagerConpoment from "../../layout/RestaurantManagerComponent"
import { MDBBtn, MDBCard, MDBCardBody, MDBInput, MDBTextArea } from "mdb-react-ui-kit";
import { Button, Card, Col, Form, Image, Row } from "react-bootstrap";
import MySpinner from "../../layout/MySpinner";
import { useEffect, useRef, useState } from "react";
import Apis, { authApi, endpoints } from "../../configs/Apis";
import cookie from "react-cookies";
import { ToastContainer, toast } from "react-toastify";

const FoodItemManager = () => {
    const restaurantId = cookie.load("restaurant")
    const [foodItems, setFoodItems] = useState([]);
    const [uniFood, setUniFood] = useState(null);
    const [loading, setLoading] = useState(false);
    const [loadingAddUpdateFood, setLoadingAddUpdateFood] = useState(false);
    const [page, setPage] = useState('index');//index --- new --- update
    const [categories, setCategories] = useState();
    const [currentCate, setCurrentCate] = useState(null);
    const avatarFood = useRef(null);
    const [selectedValue, setSelectedValue] = useState(null) // Khởi tạo giá trị state ban đầu là rỗng
    const nav = useNavigate();

    const handleSelectChange = (event) => {
        const selectedOption = event.target.value; // Lấy giá trị đã chọn từ sự kiện onChange
        setSelectedValue(selectedOption); // Cập nhật giá trị đã chọn vào state
    };

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
    }, [page])

    useEffect(() => {
        const loadCategories = async () => {
            let urlCate = `${endpoints["categories"]}?restaurantId=${restaurantId}`
            // form.append("restaurantId", restaurantId);
            let { data } = await Apis.get(urlCate);
            console.log(data);
            setCategories(data);
        }
        loadCategories();
    }, [page])

    const getFoodItem = (evt, food) => {
        evt.preventDefault();
        setPage('update');
        setCurrentCate(food.categoryfoodId);
        setSelectedValue(food.categoryfoodId.categoryfoodId);
        setUniFood(food);
        console.log(uniFood);
        // console.log(uniFood.categoryfoodId.categoryfoodId);

        if (food.categoryfoodId !== null) {
            const foundCate = categories.findIndex(cate => cate.categoryfoodId === food.categoryfoodId.categoryfoodId);
            if (foundCate > -1) {
                categories.splice(foundCate, 1)
            }
            console.log(categories)
        }
    }

    const newFood = (evt) => {
        evt.preventDefault();
        setPage('new');
    }

    const back = (evt) => {
        evt.preventDefault();
        setPage('index');
        setSelectedValue(null);
        setCurrentCate(null)
    }

    const change = (evt, field) => {
        // setUser({...user, [field]: evt.target.value})
        setUniFood(current => {
            return { ...current, [field]: evt.target.value }
        })
    }

    const addOrUpdateFoodItem = async (evt) => {
        evt.preventDefault();
        setLoadingAddUpdateFood(true);
        let form = new FormData();
        if(page === 'update'){
            form.append("foodId", uniFood.foodId);
        }
        form.append("foodName", uniFood.foodName);
        form.append("price", uniFood.price);
        form.append("description", uniFood.description);
        form.append("restaurantId", restaurantId);
        if(selectedValue !== null){
            form.append("categoryfoodId", selectedValue);
        }
        if (avatarFood.current.files[0] !== undefined) {
            form.append("avatar", avatarFood.current.files[0]);
        } else {
            form.append("avatar", new Blob());
        }
        try {
            let res = await authApi().post(endpoints['add-or-update-food'], form, {
                headers: {
                    'Content-Type': 'multipart/form-data', // Đặt tiêu đề Content-Type thành multipart/form-data
                },
            })
            setLoadingAddUpdateFood(false);
            if (res.status === 200) {
                // let url = `/food_manager/${restaurantId}`
                // nav(url)
                // back();
                setPage("index")
            }
        } catch (e) {
            console.log(e);
            setLoadingAddUpdateFood(false);
            toast.error(e.request.reponsiveText);
        }
    }

    return <>
        <div className="dasboard_big">
            <div className="dasboard_1" >
                <RestaurantManagerConpoment />
            </div>
            <div className="dasboard_2">
                <ToastContainer />
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
                                                <Button onClick={(e) => getFoodItem(e, f)} variant="primary">Chỉnh sửa</Button>
                                            </Card.Body>
                                        </Card>
                                    </Col>
                                </>
                            })}
                        </>}
                    </Row>
                </> : <>
                    {page === 'update' ? <>
                        <div>
                            <h1 className="text-center">Cặp nhật món ăn</h1>
                            <Button onClick={back}>Quay lại</Button>
                            {uniFood === null ? <MySpinner /> : <>
                                {/* update food*/}
                                <div style={{ marginBottom: '20px' }}>
                                    <MDBCard className='m-5 register_form' style={{ maxWidth: '600px', marginBottom: '20px' }}>
                                        <MDBCardBody className='px-5 register_form_child'>
                                            <div style={{ marginBottom: 22 + 'px' }}>
                                                <Image src={uniFood.avatar} rounded style={{ maxWidth: '150px', marginLeft: 35 + '%' }} />
                                                <Form.Control className="avatar_input" style={{ marginLeft: 'auto', marginRight: 'auto' }} accept=".jpg, .jpeg, .png, .gif, .bmp" type="file" ref={avatarFood} />
                                            </div>
                                            {/* <MDBInput wrapperClass='mb-4' ref={avatarFood} size='lg' id='form4' type='file' /> */}
                                            <MDBInput defaultValue={uniFood.foodName} wrapperClass='mb-4' required onChange={(e) => change(e, "foodName")} label='Tên Món' size='lg' id='form1' type='text' />
                                            {/* <MDBInput wrapperClass='mb-4' label='Your Email' size='lg' id='form2' type='email' /> */}
                                            <MDBInput wrapperClass='mb-4' defaultValue={uniFood.price} required onChange={(e) => change(e, "price")} label='Giá(VNĐ)' size='lg' id='form3' type='text' />
                                            <MDBTextArea wrapperClass='mb-4' defaultValue={uniFood.description} required onChange={(e) => change(e, "description")} label='Mô tả' size='lg' id='form4' type='text' />
                                            <Form.Select aria-label="Default select example"
                                                onChange={handleSelectChange} // Gắn sự kiện onChange để theo dõi sự thay đổi của select
                                                value={selectedValue}
                                            >
                                                {currentCate !== null ? <>
                                                    <option selected value={currentCate.categoryfoodId}>{currentCate.categoryname}</option>
                                                </> : <>
                                                    <option selected value={null}>Chưa có danh mục</option>
                                                </>}
                                                {Object.values(categories).map(c => {
                                                    return <option value={c.categoryfoodId}>{c.categoryname}</option>
                                                })}

                                            </Form.Select>
                                            <div className='d-flex flex-row justify-content-center mb-4'>
                                                {/* <MDBCheckbox name='flexCheck' id='flexCheckDefault' label='I agree all statements in Terms of service' /> */}
                                            </div>
                                            {loadingAddUpdateFood === true ? <MySpinner /> : <MDBBtn onClick={addOrUpdateFoodItem} className='mb-4 w-100 gradient-custom-4' size='lg'>Cập nhật</MDBBtn>}
                                        </MDBCardBody>
                                    </MDBCard>
                                </div>

                            </>}
                        </div>
                    </> : <>
                        {/* <h1 className="text-center">Thêm món</h1> */}
                        <Button onClick={back}>Quay lại</Button>
                        {/* giao diện new mới */}
                        <div style={{ marginBottom: '20px' }}>
                            <MDBCard className='m-5 register_form' style={{ maxWidth: '600px', marginBottom: '20px' }}>

                                <MDBCardBody className='px-5 register_form_child'>
                                    <h1 className="text-center">Thêm món</h1>
                                    {/* <MDBInput wrapperClass='mb-4' ref={avatarFood} size='lg' id='form4' type='file' /> */}
                                    <MDBInput wrapperClass='mb-4' required onChange={(e) => change(e, "foodName")} label='Tên Món' size='lg' id='form1' type='text' />
                                    {/* <MDBInput wrapperClass='mb-4' label='Your Email' size='lg' id='form2' type='email' /> */}
                                    <MDBInput wrapperClass='mb-4' required onChange={(e) => change(e, "price")} label='Giá(VNĐ)' size='lg' id='form3' type='text' />
                                    <MDBTextArea wrapperClass='mb-4' required onChange={(e) => change(e, "description")} label='Mô tả' size='lg' id='form4' type='text' />
                                    <div style={{ marginBottom: 22 + 'px' }}>
                                        <Form.Control className="avatar_input" style={{ marginLeft: 'auto', marginRight: 'auto' }} accept=".jpg, .jpeg, .png, .gif, .bmp" type="file" ref={avatarFood} />
                                    </div>
                                    <label htmlFor="cate">Danh mục</label>
                                    <Form.Select aria-label="Danh mục"
                                    id="cate"
                                    name="cate"
                                        onChange={handleSelectChange} // Gắn sự kiện onChange để theo dõi sự thay đổi của select
                                        value={selectedValue}
                                    >
                                        {currentCate !== null ? <>
                                            <option selected value={currentCate.categoryfoodId}>{currentCate.categoryname}</option>
                                        </> : <>
                                            <option selected value={null}>Chưa có danh mục</option>
                                        </>}
                                        {Object.values(categories).map(c => {
                                            return <option value={c.categoryfoodId}>{c.categoryname}</option>
                                        })}

                                    </Form.Select>
                                        
                                    <div className='d-flex flex-row justify-content-center mb-4'>
                                        {/* <MDBCheckbox name='flexCheck' id='flexCheckDefault' label='I agree all statements in Terms of service' /> */}
                                    </div>
                                    {loadingAddUpdateFood === true ? <MySpinner /> : <MDBBtn onClick={addOrUpdateFoodItem} className='mb-4 w-100 gradient-custom-4' size='lg'>Cập nhật</MDBBtn>}
                                </MDBCardBody>
                            </MDBCard>
                        </div>
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