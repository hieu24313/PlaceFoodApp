import { Link, useNavigate, useParams } from "react-router-dom";
import RestaurantManagerConpoment from "../../layout/RestaurantManagerComponent"
import { MDBBtn, MDBCard, MDBCardBody, MDBInput, MDBTextArea } from "mdb-react-ui-kit";
import { Button, Card, Col, Form, Image, Row } from "react-bootstrap";
import MySpinner from "../../layout/MySpinner";
import { useEffect, useRef, useState } from "react";
import Apis, { authApi, endpoints } from "../../configs/Apis";
import cookie from "react-cookies";
import { ToastContainer, toast } from "react-toastify";
import ReactSelect from "react-select";

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
    const [promotion, setPromotion] = useState([]);
    const [promotionsNewFood, setPromotionsNewFood] = useState("");
    const [promotionsUpdateFood, setPromotionsUpdateFood] = useState("");
    const [options, setOptions] = useState([
        // { value: 'null', label: 'Chưa có' }
    ]);
    let listPromotion;

    const handleSelectChange = (event) => {
        const selectedOption = event.target.value; // Lấy giá trị đã chọn từ sự kiện onChange
        setSelectedValue(selectedOption); // Cập nhật giá trị đã chọn vào state
    };

    const formatNumberWithCommas = (number) => {
        return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    useEffect(() => {
        const getPromotion = async () => {
            try {
                let { data } = await authApi().get(endpoints['get-promotion'], {
                    "restaurantId": restaurantId
                })
                // for (let p of data){
                //     let dv;
                //     if (p.promotionTypeId.promotionTypeId === 1){
                //         dv = '%';
                //     }
                //     else{
                //         dv = 'VNĐ';
                //     }
                //     let item = { value: p.promotionId, label: p.promotionName + " " + p.pricePromotion + " " + dv }
                //     options.push(item);
                // }

                let optionsPromotion = data.map(p => ({
                    value: p.promotionId,
                    label: `${p.promotionName} : ${formatNumberWithCommas(p.pricePromotion)} ${p.promotionTypeId.promotionTypeId === 1 ? '%' : 'VNĐ'}`
                }));
                setOptions(optionsPromotion); // Cập nhật state options
                setPromotion(data);

            } catch (e) {
                console.log(e);
            }
        }

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

        getPromotion();
        loadFoodItems();
    }, [page])

    useEffect(() => {
        const loadCategories = async () => {
            try {
                let urlCate = `${endpoints["categories"]}?restaurantId=${restaurantId}`
                // form.append("restaurantId", restaurantId);
                let { data } = await authApi().get(urlCate);
                console.log(data);
                setCategories(data);
            } catch (e) {
                console.log(e);
            }
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
        if (page === 'update') {
            form.append("foodId", uniFood.foodId);
            form.append("promotion", listPromotion);
            console.log(uniFood.foodId);
            console.log(listPromotion)
        }
        form.append("foodName", uniFood.foodName);
        form.append("price", uniFood.price);
        form.append("description", uniFood.description);
        form.append("restaurantId", restaurantId);
        if (selectedValue !== null) {
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
                                    <MDBCard className='m-5 register_form' style={{ overflow: 'auto', maxWidth: '600px', marginBottom: '20px' }}>
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
                                            <label htmlFor="cate">Danh mục</label>
                                            <Form.Select aria-label="Default select example"
                                                id="cate"
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
                                            {/* <div style={{ marginTop: '10px' }}>
                                                {promotion.length === 0 ? <></> :
                                                    <>
                                                        <label htmlFor="promotion">Khuyến Mãi</label>
                                                        <ReactSelect

                                                            id="promotion"
                                                            onChange={(selectedOptions) => {
                                                                // Lấy giá trị được chọn và cập nhật biến promotionsNewFood
                                                                listPromotion = "";
                                                                const selectedValues = selectedOptions.map(option => {
                                                                    return listPromotion = option.value + ','
                                                                });
                                                                // console.log(listPromotion)
                                                                setPromotionsNewFood(selectedValues);
                                                            }}
                                                            isMulti
                                                            options={options} /> </>}
                                            </div> */}

                                            <div style={{ marginTop: '10px' }}>
                                                {promotion.length === 0 ? <></> :
                                                    <>
                                                        <label htmlFor="promotion">Khuyến Mãi</label>
                                                        <ReactSelect
                                                            style={{}}
                                                            id="promotion"
                                                            onChange={(selectedOptions) => {
                                                                // Lấy giá trị được chọn và cập nhật biến promotionsNewFood
                                                                listPromotion = "";
                                                                selectedOptions.map(option => {
                                                                    return listPromotion += option.value + ','
                                                                });
                                                                console.log(listPromotion)
                                                                // setPromotionsNewFood(selectedValues);
                                                            }}
                                                            isMulti
                                                            options={options}
                                                            styles={{
                                                                control: (provided) => ({
                                                                    ...provided,
                                                                    maxWidth: '100%', // Thiết lập chiều rộng tối đa
                                                                }),
                                                                menu: (provided) => ({
                                                                    ...provided,
                                                                    maxHeight: '200px', // Thiết lập chiều cao tối đa
                                                                    overflowY: 'auto', // Cho phép cuộn nếu nội dung vượt quá chiều cao tối đa
                                                                }),
                                                            }}
                                                        />
                                                        {/* <ReactSelect
                                                // styles={{}}
                                                    id="promotion"
                                                    onChange={(selectedOptions) => {
                                                        // Lấy giá trị được chọn và cập nhật biến promotionsNewFood
                                                        listPromotion = "";
                                                        const selectedValues = selectedOptions.map(option => {
                                                            return listPromotion = option.value + ','
                                                        });
                                                        // console.log(listPromotion)
                                                        setPromotionsNewFood(selectedValues);
                                                    }}
                                                    isMulti
                                                    options={options} />  */}
                                                    </>}
                                            </div>
                                            {/* <div className='d-flex flex-row justify-content-center mb-4'>
                                                <MDBCheckbox name='flexCheck' id='flexCheckDefault' label='I agree all statements in Terms of service' />
                                            </div> */}
                                            {loadingAddUpdateFood === true ? <MySpinner /> : <MDBBtn style={{ marginTop: '10px' }} onClick={addOrUpdateFoodItem} className='mb-4 w-100 gradient-custom-4' size='lg'>Cập nhật</MDBBtn>}
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
                            <MDBCard className='m-5 register_form formnewfood' style={{ zIndex: '10', maxWidth: '600px', marginBottom: '20px', overflow: 'auto', overflowY: 'auto' }}>

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
                                    </div>
                                    {loadingAddUpdateFood === true ? <MySpinner /> : <MDBBtn onClick={addOrUpdateFoodItem} className='mb-4 w-100 gradient-custom-4' size='lg'>Thêm</MDBBtn>}
                                </MDBCardBody>
                            </MDBCard>
                        </div>
                    </>}
                </>}
            </div>
        </div>
    </>
}

export default FoodItemManager;