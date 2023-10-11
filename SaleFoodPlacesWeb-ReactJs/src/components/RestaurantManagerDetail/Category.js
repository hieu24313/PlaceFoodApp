import { useEffect, useState } from "react";
import cookie, { load } from "react-cookies";
import Apis, { authApi, endpoints } from "../../configs/Apis";
import RestaurantManagerConpoment from "../../layout/RestaurantManagerComponent";
import { Button, Form, Table } from "react-bootstrap";
import MySpinner from "../../layout/MySpinner";
import { Alert, Input } from "@mui/material";
import { ToastContainer, toast } from "react-toastify";


const Category = () => {

    const restaurantId = cookie.load("restaurant");
    const [categories, setCategories] = useState(null);
    const [loading, setLoading] = useState(false);
    const [btnSaveAndUpdate, setBtnSaveAndUpdate] = useState(false);
    const [category, setCategory] = useState("");
    const [loadingSave, setLoadingSave] = useState(false);
    const [loadingAdd, setLoadingAdd] = useState(false);
    const [addCate, setAddcate] = useState(false);
    const [newCate, setNewCate] = useState();


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

    useEffect(() => {
        setCategories([])
        loadCategories();
    }, [])

    const save = async (id) => {
        setLoadingSave(true);
        //disable input
        //cho nhập category
        let inputId = `input${id}`
        let input = document.getElementById(inputId);
        let result = input.value.trim();
        if (result === "" || result === null) {
            alert("Không được để trống!");
        }
        else {
            input.disabled = true;
            console.log(input.value);
            //ẩn nút lưu
            let btn_id = `btn_save${id}`;
            let btn = document.getElementById(btn_id);
            btn.style.display = "none";
            setBtnSaveAndUpdate(false);

            try {
                let { data } = await authApi().post(endpoints['add-or-update-category'], {
                    "CategoryId": id,
                    "categoryName": result,
                    "restaurantId": restaurantId
                })
                toast.success(data);
            }
            catch (e) {
                console.log(e);
                toast.error(e.request.reponsiveText);
            }
        }
        setLoadingSave(false);


    }

    const update = (id) => {
        //hiện nút lưu
        let btn_id = `btn_save${id}`;
        let btn = document.getElementById(btn_id);
        btn.style.display = "block";
        setBtnSaveAndUpdate(true);

        //cho nhập category
        let inputId = `input${id}`
        let input = document.getElementById(inputId);
        input.disabled = false;

    }


    const deleteCate = async (id, e) => {
        e.preventDefault();
        if (window.confirm("Bạn chắc chắn xóa không?") === true) {

            try {
                let res = await authApi().post(endpoints['delete-cate'], {
                    "categoryfoodId": id
                })
                toast.success(res.data);
                setCategories([])
                loadCategories();

            }
            catch (e) {
                console.log(e);
                // toast.error(e.request.reponsiveText);
            }
        } else {

        }
    }

    const pageAddCate = (e) => {
        e.preventDefault();
        setAddcate(true);
    }

    const setPageAdd = () => {
        setAddcate(false)
    }

    const addNewCate = async (e) => {
        setLoadingAdd(true);
        e.preventDefault();
        if (newCate === null || newCate === undefined || newCate.trim() === "") {
            alert("Vui lòng không để trống tên danh mục");
        }
        else {
            try {
                let { data } = await authApi().post(endpoints['add-or-update-category'], {
                    "categoryName": newCate,
                    "restaurantId": restaurantId
                })
                toast.success(data);
                setPageAdd();
                setCategories([])
                loadCategories();
            }
            catch (e) {
                console.log(e);
                toast.error(e.request.reponsiveText);
            }
        }
        setLoadingAdd(false);
    }

    return <>
        <div className="dasboard_big">
            <div className="dasboard_1" >
                <RestaurantManagerConpoment />
            </div>
            <div className="dasboard_2">
                <ToastContainer />
                <h1 style={{ marginTop: '15px' }} className="text-center">Quản Lý Danh Mục</h1>
                {/* <Button onClick={update(22)}>11</Button> */}
                <Table striped bordered hover style={{ marginTop: '15px' }}>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tên Danh Mục</th>
                            <th></th>
                            {/* <th></th> */}
                        </tr>
                    </thead>
                    <tbody>
                        {categories === null ? <MySpinner /> : <>
                            {categories.length === 0 ? <Alert className="alert-primary">Nhà hàng chưa có danh mục nào!</Alert> : <>
                                {categories.map(c => {
                                    let id_input = `input${c.categoryfoodId}`;
                                    let idbtn_save = `btn_save${c.categoryfoodId}`;
                                    return <tr>
                                        <td>{c.categoryfoodId}</td>
                                        {/* <td><Input onChange="" defaultValue={c.categoryname} /></td> */}
                                        <td style={{ display: 'flex', justifyContent: 'space-between', }} ><input id={id_input} style={{ border: 'none', color: 'black' }} disabled defaultValue={c.categoryname} />
                                            <div>
                                                {btnSaveAndUpdate === false ? <Button onClick={() => update(c.categoryfoodId)}>Sửa</Button> : <></>}
                                                <Button id={idbtn_save} style={{ display: 'none' }} onClick={() => { save(c.categoryfoodId) }} >Lưu</Button>
                                            </div>
                                        </td>
                                        <td><Button className="btn-danger" onClick={(e) => deleteCate(c.categoryfoodId, e)} >Xóa danh mục</Button></td>
                                        {/* <td><Button>Cập nhật danh mục</Button></td> */}
                                    </tr>
                                })}
                            </>}
                        </>}


                    </tbody>
                </Table>
                {addCate === false ? <Button style={{marginLeft: '10px'}} onClick={pageAddCate}>Thêm</Button> : <>
                    <div style={{ alignItems: 'center' }}>
                        <Form onSubmit={addNewCate} style={{ width: '40%', margin: 'auto' }} >
                            <h1 className="text-center">Thêm Danh Mục</h1>
                            <Form.Group className="mb-3" controlId="formBasicEmail">
                                {/* <Form.Label>Tên Danh Mục</Form.Label> */}
                                <Form.Control onChange={(e) => setNewCate(e.target.value)} type="text" placeholder="Nhập tên danh mục" />
                                {/* <Form.Text className="text-muted">
                                We'll never share your email with anyone else.
                            </Form.Text> */}
                            </Form.Group>
                            <div style={{ display: 'flex', justifyContent: 'space-around' }}>
                                {loadingAdd === true ? <></> : <Button variant="primary" type="submit">Thêm</Button>}
                                <Button className="btn-danger" style={{marginLeft: '5px'}} onClick={setPageAdd}>Hủy</Button>
                            </div>

                        </Form>
                    </div>
                </>}

            </div>
        </div>
    </>
}

export default Category;