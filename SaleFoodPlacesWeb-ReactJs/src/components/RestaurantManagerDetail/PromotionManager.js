import { useParams } from "react-router-dom";
import RestaurantManagerConpoment from "../../layout/RestaurantManagerComponent";
import { Alert, Button, Form, Table } from "react-bootstrap";
import cookie, { load } from "react-cookies";
import { useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/Apis";
import MySpinner from "../../layout/MySpinner";
import Moment from "react-moment";
import { ToastContainer, toast } from "react-toastify";

const PromotionManager = () => {
    const restaurantId = cookie.load("restaurant");
    const [promotion, setPromotion] = useState(null);
    const [checkType, setCheckType] = useState(null);
    const [checkType2, setCheckType2] = useState(null);
    const [err, setErr] = useState(null);
    const [loadingFormNew, setLoadingFormNew] = useState(false);
    const [newPromotion, setNewPromotion] = useState({
        "promotionName": "",
        "fromDate": "",
        "toDate": "",
        "price": "0",
        "typePromotion": ""
    });
    const [uniPromotion, setUniPromotion] = useState();
    const [fromDate, setFromDate] = useState();
    const [toDate, setToDate] = useState();
    const [page, setPage] = useState("new"); //new   //update
    const moment = require('moment');

    const getPromotion = async () => {
        try {
            let { data } = await authApi().get(endpoints['get-promotion'], {
                "restaurantId": restaurantId
            })

            setPromotion(data);
        } catch (e) {
            console.log(e);
        }
    }

    useEffect(() => {

        getPromotion();
    }, [])

    const reload = () => {
        setPromotion([]);
        setTimeout(() => getPromotion(), 1000)
    }

    const deletePromotion = async (id) => {
        if (window.confirm("Bạn có chắc muốn xóa khuyến mãi này?") === true) {
            try {
                let res = await authApi().post(endpoints['delete-promotion'](id));
                if (res.status === 200) {
                    toast.success(res.data);
                    reload()
                }
            } catch (e) {
                console.error(e);
                toast.error("có lỗi xảy ra");
            }
        }

    }

    const change = (evt, field) => {
        setNewPromotion(current => {
            return { ...current, [field]: evt.target.value.trim() }
        })
    }

    const check = (evt, field, bool) => {
        change(evt, field);
        setCheckType(bool);
        setErr(null)
        let p = "price"
        setNewPromotion(current => {
            return { ...current, [p]: 0 }
        })
    }

    const setPriceforNewPrice = (e, type) => {
        if (type === 1) {
            if (e.target.value > 100 || e.target.value < 0) {
                setErr("Mức khuyến mãi phải từ 0% đến 100%");
            }
            else {
                setErr(null)
                change(e, "price");
            }
        } else {
            setErr(null)
            change(e, "price");
        }
    }

    // const date = (e) => {
    //     setD(e.tar)
    // }

    const addNewPromotion = async (e) => {
        e.preventDefault();
        let price = `${newPromotion['price']}`;

        if (newPromotion['typePromotion'].trim() === "") {
            setErr("Vui lòng chọn loại khuyến mãi");
        }
        else if (price.trim() === "" || price === "0") {
            setErr("Vui lòng nhập giá");
        } else {
            setLoadingFormNew(true);
            setCheckType(null)
            try {
                let res = await authApi().post(endpoints['add-or-update-promotion'], {
                    "promotionName": newPromotion['promotionName'],
                    "fromDate": newPromotion['fromDate'],
                    "toDate": newPromotion['toDate'],
                    "price": newPromotion['price'],
                    "typePromotion": newPromotion['typePromotion'],
                    "restaurantId": restaurantId
                })
                if (res.status === 200) {
                    toast.success(res.data);
                    reload();
                }
            } catch (e) {
                console.log(e);
            }
            setErr(null)
        }
        setLoadingFormNew(false)


    }

    const formatNumberWithCommas = (number) => {
        if (number !== null && number !== undefined) {
            return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }
    }

    const get_Uni_Promotion = async (id) => {
        console.log(id)
        try {
            let e = `${endpoints['get-uni-promotion']}?promotionId=${id}`;
            let { data } = await authApi().get(e);
            setUniPromotion(data);
            setCheckType2(null);
            console.log(data);
            if (data['fromDate'] !== null) {
                let fD = new Date(data['fromDate'] + 25200000).toISOString().split('T')[0]
                let field = 'fromDate'
                setFromDate(fD);
                setUniPromotion(current => {
                    return { ...current, [field]: fD }
                })
            }
            if (data['toDate'] !== null) {
                let tD = new Date(data['toDate'] + 25200000).toISOString().split('T')[0]
                let field = 'toDate'
                setToDate(tD);
                setUniPromotion(current => {
                    return { ...current, [field]: tD }
                })
            }

            const element = document.getElementById('scroll');

            // Sử dụng phương thức scrollIntoView để cuộn đến đối tượng
            element.scrollIntoView({ behavior: 'smooth' }); // "smooth" để cuộn mượt


            console.log(data['pricePromotion'])
            // console.log(toDate)
            setPage("update");
            if (data['promotionTypeId'].promotionTypeId === 1) {
                setCheckType2(false);
            } else {
                setCheckType2(true);
            }
            // setTimeout(() => {
                let input = document.querySelector('.input_price');
                input.value = data['pricePromotion']
            // }, 1000)
        } catch (e) {
            console.log(e);
        }
    }

    const changeUpdate = (evt, field) => {
        setUniPromotion(current => {
            return { ...current, [field]: evt.target.value.trim() }
        })
    }

    const checkUpdate = (evt, field) => {
        changeUpdate(evt, field);
        setErr(null)
        // let p = "price"
        // setUniPromotion(current => {
        //     return { ...current, [p]: 0 }
        // })
    }

    const setPriceforUpdatePrice = (e, type) => {
        if (type === 1) {
            if (e.target.value > 100 || e.target.value < 0) {
                setErr("Mức khuyến mãi phải từ 0% đến 100%");
            }
            else {
                setErr(null)
                changeUpdate(e, "price");
            }
        } else {
            setErr(null)
            changeUpdate(e, "price");
        }
    }

    const update = async (e) => {
        e.preventDefault()
        console.log(uniPromotion);
        let price = `${uniPromotion['price']}`;
        let f = 'promotionTypeId';
        setUniPromotion(current => {
            return { ...current, [f]: uniPromotion['promotionTypeId'].promotionTypeId === 1 ? 1 : 2 }
        })


        if (uniPromotion['promotionTypeId'] === "") {
            setErr("Vui lòng chọn loại khuyến mãi");
        }
        else if (price.trim() === "" || price === "0") {
            setErr("Vui lòng nhập giá");
        } else {
            setLoadingFormNew(true);
            setCheckType(null)
            try {
                let res = await authApi().post(endpoints['add-or-update-promotion'], {
                    "promotionId": uniPromotion['promotionId'],
                    "promotionName": uniPromotion['promotionName'],
                    "fromDate": uniPromotion['fromDate'],
                    "toDate": uniPromotion['toDate'],
                    "price": uniPromotion['price'],
                    "typePromotion": uniPromotion['promotionTypeId'].promotionTypeId === 1 ? 1 : 2,
                    "restaurantId": restaurantId
                })
                if (res.status === 200) {
                    toast.success(res.data);
                    setPage('new')
                    reload();
                }
            } catch (e) {
                console.log(e);
            }
            setErr(null)
        }
        setLoadingFormNew(false)
    }

    const back = () => {
        setUniPromotion(null);
        setPage('new');
        
        
    }

    const click = (f) => {
        // <Link to={<Map f={f} />} />
    }




    return <>
        <div className="dasboard_big">
            <div className="dasboard_1">
                <RestaurantManagerConpoment />
            </div>
            <div className="dasboard_2">
                <ToastContainer />
                <h1 className="text-center" style={{marginTop: '15px', marginBottom: '15px'}}>Trang quản lý khuyến mãi</h1>
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tên</th>
                            <th>Từ Ngày</th>
                            <th>Đến Ngày</th>
                            <th>Giá</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {promotion === null ? <MySpinner /> : <>
                            {promotion.length === 0 ? <Alert className="alert-primary">Nhà Hàng Chưa Có Khuyến Mãi Nào!</Alert> : <>
                                {promotion.map(p => {
                                    return <tr>
                                        <td>{p.promotionId}</td>
                                        <td>{p.promotionName}</td>
                                        <td>{p.fromDate === null ? <>Chưa có</> : <Moment format="DD/MM/YYYY">{p.fromDate}</Moment>}</td>
                                        <td>{p.toDate === null ? <>Chưa có</> : <Moment format="DD/MM/YYYY">{p.toDate}</Moment>}</td>
                                        {p.promotionTypeId.promotionTypeId === 1 ? <td>{p.pricePromotion} %</td> : <td>{formatNumberWithCommas(p.pricePromotion)} VNĐ</td>}
                                        <td>
                                            <div style={{ display: 'flex' }}>
                                                <Button onClick={() => get_Uni_Promotion(p.promotionId)} className="btn-primary">Sửa</Button>
                                                <Button style={{ marginLeft: '5px' }} onClick={() => deletePromotion(p.promotionId)} className="btn-danger">Xóa</Button>
                                            </div>
                                        </td>
                                    </tr>
                                })}
                            </>}
                        </>}
                    </tbody>
                </Table>

                <div id="scroll">
                    {page === "new" ? <>
                        {/* thêm mới */}
                        {loadingFormNew === false ? <>
                            <Form onSubmit={addNewPromotion} style={{ maxWidth: '600px', margin: 'auto'}} >
                                <h1 className="text-center text-primary">Thêm Khuyến Mãi</h1>
                                <Form.Group className="mb-3" controlId="formBasicNamePromotion">
                                    <Form.Label>Tên khuyến mãi</Form.Label>
                                    <Form.Control type="text" required onChange={(e) => { change(e, "promotionName") }} placeholder="Nhập tên khuyến mãi" />
                                    {/* <Form.Text className="text-muted">
                            </Form.Text> */}
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formFromDate">
                                    <Form.Label>Ngày Bắt Đầu</Form.Label>
                                    <Form.Control type="date" onChange={(e) => { change(e, "fromDate") }} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formToDate">
                                    <Form.Label>Ngày Kết Thúc</Form.Label>
                                    <Form.Control type="date" onChange={(e) => { change(e, "toDate") }} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formType">
                                    <Form.Check name="typePromotion" value="1"
                                        onChange={(e) => {
                                            // setD(e.target.value)
                                            check(e, "typePromotion", false)
                                        }}
                                        type="radio" label="Giảm theo %" />

                                    <Form.Check name="typePromotion" value="2"
                                        onChange={(e) => {
                                            check(e, "typePromotion", true)
                                        }}
                                        type="radio" label="Giảm Theo Giá" />

                                    {checkType === false ? <>
                                        <Form.Label>Giá Tiền(%)</Form.Label>
                                        <Form.Control type="number" defaultValue={newPromotion['price']} itemID="input_percent" onChange={(e) => setPriceforNewPrice(e, 1)} />
                                    </> : <>
                                        {checkType === true ? <>
                                            <Form.Label>Giá Tiền (VNĐ)</Form.Label>
                                            <Form.Control type="number" defaultValue={newPromotion['price']} itemID="input_price" onChange={(e) => setPriceforNewPrice(e, 2)} />
                                        </> : <></>}
                                    </>}

                                    {err !== null ? <Alert className="alert-danger">{err}</Alert> : <> </>}



                                </Form.Group>
                                {/* <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Giá Tiền</Form.Label>
                            <Form.Control type="text" onChange={e => setD(e.target.value)} />
                        </Form.Group> */}
                                <Button variant="primary" style={{margin: 'auto auto'}} id="btn-New-Promotion" type="submit">
                                    Thêm
                                </Button>
                            </Form>
                        </> : <MySpinner />}
                    </> : <>
                        {/* update */}
                        {loadingFormNew === false ? <>
                            <Form onSubmit={update} style={{ maxWidth: '600px', margin: 'auto', border: ''}}>
                                <h1 className="text-center text-primary">Cập Nhật Khuyến Mãi</h1>
                                <Form.Group className="mb-3" controlId="formBasicNamePromotion">
                                    <Form.Label>Tên khuyến mãi</Form.Label>
                                    <Form.Control type="text" defaultValue={uniPromotion.promotionName} required onChange={(e) => { changeUpdate(e, "promotionName") }} placeholder="Nhập tên khuyến mãi" />
                                    {/* <Form.Text className="text-muted">
                            </Form.Text> */}
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formFromDate">
                                    <Form.Label>Ngày Bắt Đầu</Form.Label>
                                    <Form.Control type="date" onChange={(e) => { changeUpdate(e, "fromDate") }} defaultValue={fromDate} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formToDate">
                                    <Form.Label>Ngày Kết Thúc</Form.Label>
                                    <Form.Control type="date" onChange={(e) => { changeUpdate(e, "toDate") }} defaultValue={toDate} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="formType">

                                    {/* {uniPromotion.promotionTypeId.promotionTypeId === 1 ? <> */}
                                    {/* <Form.Check name="typePromotion" value="1"
                                        {...uniPromotion.promotionTypeId.promotionTypeId === 1 ? <>checked </> : <> </>}
                                        // {...uniPromotion.promotionTypeId.promotionTypeId === 1 ? checked : <></>}
                                        onChange={(e) => {
                                            // setD(e.target.value)
                                            checkUpdate(e, "typePromotion", false)
                                        }}
                                        type="radio" label="Giảm theo %" />



                                    <Form.Check name="typePromotion" value="2"
                                        {...uniPromotion.promotionTypeId.promotionTypeId === 2 ? <>checked </> : <> </>}
                                        onChange={(e) => {
                                            checkUpdate(e, "typePromotion", true)
                                        }}
                                        type="radio" label="Giảm Theo Giá" /> */}

                                    {/* </> : <>
                                        <Form.Check name="typePromotion" value="1"
                                            onChange={(e) => {
                                                // setD(e.target.value)
                                                checkUpdate(e, "typePromotion", false)
                                            }}
                                            type="radio" label="Giảm theo %" />

                                        <Form.Check name="typePromotion" value="2"
                                            checked
                                            onChange={(e) => {
                                                checkUpdate(e, "typePromotion", true)
                                            }}
                                            type="radio" label="Giảm Theo Giá" />

                                    </>} */}


                                    {uniPromotion.promotionTypeId.promotionTypeId === 1 ? <>
                                        <Form.Check name="typePromotion" value="1"
                                            checked
                                            // {...uniPromotion.promotionTypeId.promotionTypeId === 1 ? checked : <></>}
                                            onChange={(e) => {
                                                // setD(e.target.value)
                                                checkUpdate(e, "typePromotion", false)
                                            }}
                                            type="radio" label="Giảm theo %" />



                                        {/* <Form.Check name="typePromotion" value="2"
                                            onChange={(e) => {
                                                checkUpdate(e, "typePromotion", true)
                                            }}
                                            type="radio" label="Giảm Theo Giá" /> */}
                                    </> : <>
                                        {/* <Form.Check name="typePromotion" value="1"

                                            // {...uniPromotion.promotionTypeId.promotionTypeId === 1 ? checked : <></>}
                                            onChange={(e) => {
                                                // setD(e.target.value)
                                                checkUpdate(e, "typePromotion")
                                                setCheckType2(false);
                                            }}
                                            type="radio" label="Giảm theo %" /> */}



                                        <Form.Check name="typePromotion" value="2"
                                            checked
                                            onChange={(e) => {
                                                checkUpdate(e, "typePromotion")
                                                setCheckType2(true);
                                            }}
                                            type="radio" label="Giảm Theo Giá" />
                                    </>}


                                    {checkType2 === false ? <>
                                        <Form.Label>Giá Tiền (%)</Form.Label>
                                        <Form.Control className="input_price" type="number" defaultValue={uniPromotion.pricePromotion} itemID="input_percent" onChange={(e) => setPriceforUpdatePrice(e, 1)} />
                                    </> : <>
                                        {checkType2 === true ? <>
                                            <Form.Label>Giá Tiền (VNĐ)</Form.Label>
                                            <Form.Control type="number" className="input_price" defaultValue={uniPromotion.pricePromotion} itemID="input_price" onChange={(e) => setPriceforUpdatePrice(e, 2)} />
                                        </> : <></>}
                                    </>}

                                    {err !== null ? <Alert className="alert-danger">{err}</Alert> : <> </>}



                                </Form.Group>
                                {/* <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Giá Tiền</Form.Label>
                            <Form.Control type="text" onChange={e => setD(e.target.value)} />
                        </Form.Group> */}
                                <Button variant="primary" id="btn-New-Promotion" type="submit">
                                    Sửa
                                </Button>
                                {/* <Button variant="primary" id="btn-New-Promotion" onClick={back} type="submit">
                                    Hủy
                                </Button> */}
                            </Form>
                        </> : <MySpinner />}
                    </>}


                </div>
            </div>
        </div>
    </>
}
export default PromotionManager;