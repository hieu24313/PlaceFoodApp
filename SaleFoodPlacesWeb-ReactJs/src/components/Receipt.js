import { useContext, useEffect, useState } from "react";
import { Accordion, Alert, Form, Image, Nav } from "react-bootstrap";
import { Link } from "react-router-dom";
import { MyUserContext } from "../App";
import Apis, { authApi, endpoints } from "../configs/Apis";
import MySpinner from "../layout/MySpinner";
import Moment from "react-moment";
import { MDBBtn, MDBCard, MDBCardBody, MDBCol, MDBContainer, MDBIcon, MDBRow, MDBTable, MDBTableBody, MDBTableHead, MDBTypography } from "mdb-react-ui-kit";
import ProfileComponents from "../layout/ProfileComponents";
import { ToastContainer, toast } from "react-toastify";


const Receipt = () => {

    const [receipt, setReceipt] = useState([]);
    const [user,] = useContext(MyUserContext);
    const [activeKey, setActiveKey] = useState('0');
    const [receiptDetail, setReceiptDetail] = useState();
    const [loading, setloading] = useState(false);
    const [acceptReceipt, setAcceptReceipt] = useState()
    const notify = (x) => toast(x);


    const loadReceipt = async () => {
        try {
            // let form = new FormData();
            // form.append("userId", userId)
            let e = `${endpoints["receipt"]}?userId=${user.userId}`
            let res = await authApi().get(e);
            setReceipt(res.data);
        } catch (err) {
            console.log(err);
        }
    }

    useEffect(() => {
            
        loadReceipt();
    }, [user])

    const loadReceiptDetail = async (receiptId) => {
        try {
            setloading(true);
            // let urlDetail = endpoints["receiptDetail"];
            let { data } = await authApi().get(endpoints['receiptDetail'](receiptId));
            setReceiptDetail(data);
            setloading(false);
        } catch (er) {
            console.log(er);
            setloading(false);
        }
    }

    const handleAccordionClick = (eventKey) => {
        
            // if (activeKey === eventKey) {
            //     setTimeout(() => {
            //     setActiveKey(null); // Đóng phần body khi click lại vào cùng một Accordion.Header
            // }, 5000)
            // } else {
            //     setActiveKey(eventKey); // Mở phần body khi click vào Accordion.Header khác
            // }
            if(activeKey === eventKey){
                setActiveKey(null);
            }
            else{
                setActiveKey(eventKey);
            }

        
    };

    const disableAcceptbtn = (receiptId) => {
        if(receiptId !== null){
            setTimeout(() => {
                let btn = document.getElementById(receiptId);
                    if(btn !== null){
                        btn.disabled = true;
                    }
            }, 1000)
            
        }
        // console.log("id hoa don" + receiptId);
    }

    const acceipt = async (receiptId) => {
        try{
            let { data } = await authApi().post(endpoints['accept-receipt'](receiptId));
            notify(data);
            setReceiptDetail(null)
            // setTimeout(() => {
                loadReceipt();
                loadReceiptDetail(receiptId);
            // }, 1000)
            // disableAcceptbtn(receiptId);
        }catch(err){
            console.log(err);
        }
    } 

    if (user === null)
        return <Alert className="alert-danger">Vui lòng đăng nhập</Alert>

    // if (receipt.length === 0)
    //     return <Alert classname="alert-success" >Bạn chưa có hóa đơn nào!!</Alert>


    return <>

        <div>
            <h1 className="text-center text-primary">Trang Chi Tiết Hóa Đơn</h1>

            <div className="contain_info">

                <ProfileComponents />
                <ToastContainer />
                
                <div className="contain_info_2">
                {receipt.length === 0 ? <Alert classname="alert-info" >Bạn chưa có hóa đơn nào!!</Alert> : ""}
                    {receipt === 0 ? <Alert classname="alert-success" >Bạn chưa có hóa đơn nào!!</Alert> : <>
                        {receipt == null ? <MySpinner /> : Object.values(receipt).map(r => {
                            let count = 0;
                            let amount = 0;
                            return <Accordion className="mt-2" activeKey={activeKey}>
                                {/* <Accordion.Item eventKey={r.receiptId} onClick={() => handleAccordionClick(r.receiptId)}> */}
                                <Accordion.Item eventKey={r.receiptId} >
                            {console.log(r)}
                                    <Accordion.Header onClick={() =>{loadReceiptDetail(r.receiptId); handleAccordionClick(r.receiptId)}} >Hóa Đơn {r.receiptId} - {r.statusReceiptId.statusReceipt} <Moment style={{ marginLeft: 'auto' }} format="DD-MM-YYYY HH:mm">{r.receiptDate}</Moment> </Accordion.Header>
                                    {/* <Accordion.Body id="body_detail" onLoad={setTimeout(()=>{let c =document.getElementById('body_detail'); c.onclick = null} ,300)} > */}
                                    <Accordion.Body id="body_detail"  >
                                        {loading === true ? <MySpinner /> : <>
                                            {receiptDetail === null || receiptDetail === undefined ? "" : <>
                                                <MDBContainer className="py-5">
                                                    <MDBCard className="p-4">
                                                        <MDBCardBody>
                                                            <MDBContainer className="mb-2 mt-3">
                                                                <MDBRow className="d-flex align-items-baseline">
                                                                    <MDBCol xl="9">
                                                                        <p style={{ color: "#7e8d9f", fontSize: "20px" }}>
                                                                            <strong>Mã Hóa Đơn: {r.receiptId}</strong>
                                                                        </p>
                                                                    </MDBCol>
                                                                    <MDBCol xl="3" className="float-end">
                                                                        <MDBBtn
                                                                            color="light"
                                                                            ripple="dark"
                                                                            className="text-capitalize border-0"
                                                                        >
                                                                            <MDBIcon fas icon="print" color="primary" className="me-1" />
                                                                            Print
                                                                        </MDBBtn>
                                                                        <MDBBtn
                                                                            color="light"
                                                                            ripple="dark"
                                                                            className="text-capitalize border-0 ms-2"
                                                                        >
                                                                            <MDBIcon
                                                                                far
                                                                                icon="file-pdf"
                                                                                color="danger"
                                                                                className="me-1"
                                                                            />
                                                                            Export
                                                                        </MDBBtn>
                                                                        <hr />
                                                                    </MDBCol>
                                                                </MDBRow>
                                                            </MDBContainer>
                                                            <MDBContainer>
                                                                <MDBCol md="12" className="text-center">
                                                                    <p className="pt-0"><h1>HÓA ĐƠN</h1></p>
                                                                    <MDBIcon
                                                                        fab
                                                                        icon="mdb"
                                                                        size="4x"
                                                                        className="ms-0 "
                                                                        style={{ color: "#5d9fc5" }}
                                                                    />

                                                                </MDBCol>
                                                            </MDBContainer>
                                                            <MDBRow>
                                                                <MDBCol xl="8">
                                                                    <MDBTypography listUnStyled>
                                                                        <li className="text-muted">
                                                                            Tên Khách Hàng: <span style={{ color: "#5d9fc5" }}>{user.first} {user.lastname}</span>
                                                                        </li>
                                                                        <li className="text-muted">{user.location}</li>
                                                                        <li className="text-muted">{user.location}</li>
                                                                        <li className="text-muted">
                                                                            <MDBIcon fas icon="phone-alt" /> {user.phonenumber}
                                                                        </li>
                                                                    </MDBTypography>
                                                                </MDBCol>
                                                                <MDBCol xl="4">
                                                                    <p className="text-muted">Hóa Đơn</p>
                                                                    <MDBTypography listUnStyled>
                                                                        <li className="text-muted">
                                                                            <MDBIcon fas icon="circle" style={{ color: "#84B0CA" }} />
                                                                            <span className="fw-bold ms-1">Mã Hóa Đơn:</span>#{r.receiptId}
                                                                        </li>
                                                                        <li className="text-muted">
                                                                            <MDBIcon fas icon="circle" style={{ color: "#84B0CA" }} />
                                                                            <span className="fw-bold ms-1">Ngày Tạo : </span> <Moment format="DD-MM-YYYY HH:mm">{r.receiptDate}</Moment>
                                                                        </li>
                                                                        <li className="text-muted">
                                                                            <MDBIcon fas icon="circle" style={{ color: "#84B0CA" }} />
                                                                            <span className="fw-bold ms-1">Trạng Thái: </span>
                                                                            <span className="badge bg-warning text-black fw-bold ms-1">
                                                                                {r.statusReceiptId.statusReceipt}
                                                                            </span>
                                                                        </li>
                                                                    </MDBTypography>
                                                                </MDBCol>
                                                            </MDBRow>
                                                            <MDBRow className="my-2 mx-1 justify-content-center">
                                                                <MDBTable striped borderless>
                                                                    <MDBTableHead
                                                                        className="text-white"
                                                                        style={{ backgroundColor: "#84B0CA" }}
                                                                    >
                                                                        <tr>
                                                                            <th scope="col">#</th>
                                                                            <th scope="col">Tên Món Ăn</th>
                                                                            <th scope="col">Số Lượng</th>
                                                                            <th scope="col">Giá</th>
                                                                            <th scope="col">Tổng</th>
                                                                        </tr>
                                                                    </MDBTableHead>
                                                                    <tbody>
                                                                        {Object.values(receiptDetail).map(rd => {
                                                                            amount += rd.amount;
                                                                            count++;
                                                                            return <tr>
                                                                                <th scope="row">{count}</th>
                                                                                <td>{rd.fooditemId.foodName}</td>
                                                                                <td>{rd.quantity}</td>
                                                                                <td>{rd.unitPrice} VNĐ</td>
                                                                                <td>{rd.amount} VNĐ</td>
                                                                            </tr>
                                                                        })}


                                                                    </tbody>
                                                                </MDBTable>
                                                            </MDBRow>
                                                            <MDBRow>
                                                                <MDBCol xl="8">
                                                                    <p className="ms-3">
                                                                        {/* Add additional notes and payment information */}
                                                                    </p>
                                                                </MDBCol>
                                                                <MDBCol xl="3">
                                                                    <MDBTypography listUnStyled>
                                                                        <li className="text-muted ms-3">
                                                                            <span class="text-black me-4">Thuế: </span>(15%)
                                                                        </li>
                                                                        {/* <li className="text-muted ms-3 mt-2">
                                                                            <span class="text-black me-4">Tax(15%)</span>$111
                                                                        </li> */}
                                                                    </MDBTypography>
                                                                    <p className="text-black float-start">
                                                                        <span className="text-black me-3"> Tổng: </span>
                                                                        <span style={{ fontSize: "18px" }}>{amount} VNĐ</span>
                                                                    </p>
                                                                </MDBCol>
                                                            </MDBRow>
                                                            <hr />
                                                            <MDBRow>
                                                                <MDBCol xl="10">
                                                                    <p>Cảm ơn bạn đã sử dụng dịch vụ!!!</p>
                                                                </MDBCol>
                                                                <MDBCol xl="2">
                                                                    <MDBBtn
                                                                        id={r.receiptId}
                                                                        onClick={() => acceipt(r.receiptId)}
                                                                        className="text-capitalize"
                                                                        style={{ backgroundColor: "#60bdf3" }}
                                                                    >
                                                                        {r.statusReceiptId.statusReceiptId == 1 ? "Xác Nhận Đơn" :
                                                                            <>
                                                                                 Đã Xác Nhận
                                                                                 {console.log(r.statusReceiptId)}
                                                                                 {disableAcceptbtn(r.receiptId)}
                                                                            </>
                                                                        }
                                                                    </MDBBtn>
                                                                </MDBCol>
                                                            </MDBRow>
                                                        </MDBCardBody>
                                                    </MDBCard>
                                                </MDBContainer>
                                            </>}
                                        </>}

                                    </Accordion.Body>
                                </Accordion.Item>
                            </Accordion>
                        })}
                    </>}


                </div>
            </div>
        </div>
    </>
}
export default Receipt;