/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


function deleteShelfLife(path, id) {
    if (confirm("Muốn xóa không") === true) {
        fetch(path, {
            method: "delete"
        }).then(res => {
            if (res.status === 204) {
                location.reload();
            } else {
                alert("Bug rồi ba");
            }
        });
    }
}