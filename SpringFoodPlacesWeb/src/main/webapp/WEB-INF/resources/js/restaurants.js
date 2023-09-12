/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


function deleteRestaurant(path, id) {
    if (confirm("Muốn xóa không") === true) {
        fetch(path, {
            method: "delete"
        }).then(res => {
            if (res.status === 204) {
                location.reload();
            } else {
                alert("Có lỗi xảy ra!");
            }
        });
    }
}

function getUsersByRoleId() {
    return fetch("http://localhost:8080/SpringFoodPlacesWeb/api/server/admin/users/roleId/2", {
        method: "get"
    }).then(res => {
        return res.json();
    }).then(data => {
        var u = data;
        console.log(u);
        return u;
    });
}

$(document).ready(async function () {
    
    $("#search_userId").on("keyup focus", async function () {
        const users = await getUsersByRoleId();
        $("#suggest").show();
        let h = "";
        let t = $("#search_userId").val();
        console.log(users[0].username);


        for (let u of users)
            if (u.firstname !== null || u.lastname !== null) {
                if (u.firstname.includes(t) >= 0)
                    h += `<option value=${u.userId}><a id=${u.userId} href="javascript:;">${u.firstname} ${u.lastname}</a></option>`;
                
                else if (u.lastname.includes(t) >= 0) {
                    h += `<option value=${u.userId}><a id=${u.userId} href="javascript:;">${u.firstname} ${u.lastname}</a></option>`;
                }
//                else {
//                    let fullName = u.firstname + " " + u.lastname;
//                    console.log("fullname" + fullName);
//                    if (fullName.search(t) >= 0) {
//                        h += `<option><a id=${u.userId} href="javascript:;">${u.firstname} ${u.lastname}</a></option>`;
//                    }
//                }
            }

        $("#suggest").html(h);
    });

    $("#suggest").on("click", "a", function () {

        let t = $(this).text();
        $("#search_userId").val(t);
        let id = $(this).attr("id");

        console.log(id);
//        $("#load_userId_js").html(id);
        $("#load_userId_js").val(id);
        $("#suggest").html("");
        $("#suggest").hide();
    });
});

function getPicture() {
    const dropContainer = document.getElementById("dropcontainer");
    const fileInput = document.getElementById("file");

    dropContainer.addEventListener("dragover", (e) => {
        // prevent default to allow drop
        e.preventDefault();
    }, false);

    dropContainer.addEventListener("dragenter", () => {
        dropContainer.classList.add("drag-active");
    });

    dropContainer.addEventListener("dragleave", () => {
        dropContainer.classList.remove("drag-active");
    });

    dropContainer.addEventListener("drop", (e) => {
        e.preventDefault();
        dropContainer.classList.remove("drag-active");
        fileInput.files = e.dataTransfer.files;
    });
}

$(document).ready(function () {
    getPicture();
});

function delayScrollToClickedPosition(event) {
    event.preventDefault(); // Ngăn chặn hành vi mặc định của thẻ <a>

    const clickedX = event.clientX; // Lấy tọa độ X của sự kiện click
    const clickedY = event.clientY; // Lấy tọa độ Y của sự kiện click

    // Chuyển hướng đến URL mới
    window.location.href = event.target.href;

    // Trì hoãn cuộn trang sau 100ms (có thể điều chỉnh thời gian theo nhu cầu)
    setTimeout(() => {
        const scrollX = window.scrollX + clickedX; // Tính toán tọa độ X cần cuộn tới
        const scrollY = window.scrollY + clickedY; // Tính toán tọa độ Y cần cuộn tới
        window.scrollTo(scrollX, scrollY); // Cuộn trang đến vị trí tương ứng
    }, 1000);
}

$(document).ready(function (event) {
    delayScrollToClickedPosition(event);
});

function getid(){
    let id = document.getElementById("suggest");
    let input_id = document.querySelector(".getId");
    
    input_id.value = id.value;
}


