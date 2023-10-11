import { useParams } from "react-router-dom";
import RestaurantManagerConpoment from "../../layout/RestaurantManagerComponent";
import { useContext, useEffect, useState } from "react";
import { authApi, endpoints } from "../../configs/Apis";
import { Bar } from "react-chartjs-2";
import Chart from 'chart.js/auto';
import { CategoryScale } from 'chart.js';
import { Alert, Button, Form } from "react-bootstrap";
Chart.register(CategoryScale);

const Revenus = () => {
  const { restaurantId } = useParams();
  const [statsRevenue, setStatsRevenue] = useState({ labels: [], data: [] });
  const [formDate, setFromDate] = useState();
  const [toDate, setToDate] = useState();
  const [quarter, setQuarter] = useState();
  const [year, setYear] = useState();

  useEffect(() => {
    const loadStatsRevenue = async () => {
      try {
        console.log(restaurantId);
        let e = `${endpoints['get-stats-revenue']}?restaurantId=${restaurantId}`;
        let res = await authApi().get(e);

        const statsData = res.data;
        const labels = statsData.map((stat) => stat[1]);
        const data = statsData.map((stat) => stat[2]);

        setStatsRevenue({ labels, data });
        console.log(statsData);
      } catch (e) {
        console.log(e);
      }
    };
    loadStatsRevenue();
  }, [restaurantId]);

  const search = async (evt) => {
    evt.preventDefault();
    try {
      let e = `${endpoints['get-stats-revenue']}?restaurantId=${restaurantId}&fromDate=${formDate}&toDate=${toDate}&quarter=${quarter}&quarter-year=${year}`;
      let res = await authApi().get(e);
      console.log(e)
      console.log(res.data)
      const statsData = res.data;
      const labels = statsData.map((stat) => stat[1]);
      const data = statsData.map((stat) => stat[2]);

      setStatsRevenue({ labels, data });
    } catch (e) {
      console.log(e);
    }
  }

  const data1 = {
    labels: statsRevenue.labels,
    datasets: [
      {
        label: "Doanh thu (VND)",
        data: statsRevenue.data,
        backgroundColor: [
          "red",
          "blue",
          "yellow",
          "green",
          "purple",
          "orange",
        ],
      },
    ],
  };

  return (
    <div className="dasboard_big">
      <div className="dasboard_1">
        <RestaurantManagerConpoment />
      </div>
      <div className="dasboard_2">
        <h1 className="text-center" style={{ marginTop: '10px' }}>Doanh thu của nhà hàng</h1>
        <div style={{width: '50%', marginLeft: '300px'}}>
          {/* <h1 className="text-center">Lọc</h1> */}
          <Form onSubmit={search} style={{display: 'flex', flexDirection: 'column'}} >
            <div style={{ display: 'flex', justifyContent: 'space-evenly', alignItems: 'center' }}>
              <Form.Group style={{ marginLeft: '10px' }} className="mb-3" controlId="formFromDate">
                <Form.Label>Từ ngày</Form.Label>
                <Form.Control type="date" onChange={(e) => setFromDate(e.target.value)} placeholder="Nhập ngày bắt đầu" />
                {/* <Form.Text className="text-muted">
              We'll never share your email with anyone else.
            </Form.Text> */}
              </Form.Group>

              <Form.Group style={{ marginLeft: '10px' }} className="mb-3" controlId="formToDate">
                <Form.Label>Đến ngày</Form.Label>
                <Form.Control type="date" onChange={(e) => setToDate(e.target.value)} placeholder="Nhập kết thúc" />
                {/* <Form.Text className="text-muted">
              We'll never share your email with anyone else.
            </Form.Text> */}
              </Form.Group>

              <Form.Group style={{ marginLeft: '10px' }} className="mb-3" controlId="formQuarter">
                <Form.Label>Quý</Form.Label>
                <Form.Control type="number" min="1" max="4" onChange={(e) => setQuarter(e.target.value)} placeholder="Quý" />
                {/* <Form.Text className="text-muted">
              We'll never share your email with anyone else.
            </Form.Text> */}
              </Form.Group>

              <Form.Group style={{ marginLeft: '10px' }} className="mb-3" controlId="formYear">
                <Form.Label>Năm</Form.Label>
                <Form.Control type="number" onChange={(e) => setYear(e.target.value)} placeholder="Năm" />
                {/* <Form.Text className="text-muted">
              We'll never share your email with anyone else.
            </Form.Text> */}
              </Form.Group>
            </div>
            <Button className="btn" variant="primary" type="submit">
              Lọc
            </Button>
          </Form>
        </div>
        <div id="RevenueChart" style={{ width: '50%', margin: 'auto auto', marginTop: '30px' }}>
          {statsRevenue.labels.length > 0 && statsRevenue.data.length > 0 ? (
            <Bar data={data1} />
          ) : (
            <>
              <Bar data={data1} />
              <Alert className="alert-danger">Không có sản phẩm nào được bán trong thời gian này!</Alert>
            </>
          )}
        </div>

        

      </div>
    </div>
  );
};

export default Revenus;
