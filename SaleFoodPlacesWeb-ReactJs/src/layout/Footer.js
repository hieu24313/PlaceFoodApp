import { MDBIcon } from 'mdb-react-ui-kit';
import '../resources/css/Footer.css'
import avatarweb from '../resources/img/avatarweb.png'

const Footer = () => {
    return <>
        <footer>
            <div className='div_footer' style={{display: 'flex', justifyContent: 'space-between'}}>
                <div className=" mt-4 p-5 ">
                    <h1>Nguyễn Minh Hiếu &copy; 2023 - Đồ Án Ngành</h1>
                    <p>Khoa CNTT, Đại học Mở Tp.HCM</p>
                    <span>Liên hệ: </span>
                    <a href="https://github.com/hieu24313" ><MDBIcon fab icon='facebook-f' /> </a>
                    {/* <a href="https://www.facebook.com/minhhieu4"><MDBIcon fab icon='github' /></a> */}
                </div>
                <div style={{width: '200px'}}>
                    <img style={{width: '100%'}} src={avatarweb} alt="Đồ án nghành" />
                </div>
            </div>
        </footer>
    </>
}
export default Footer;