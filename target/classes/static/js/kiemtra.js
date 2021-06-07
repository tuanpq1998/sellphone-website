// JavaScript Document
// Các hàm kiểm tra
function Kiemtradulieu()
{
	var kt=document.getElementsByClassName("kiemtra");
	for(i=0;i<kt.length;i++)
	{
		if(kt.item(i).value=="")
		{
			Swal.fire("Error", kt.item(i).getAttribute("data_error"), "error");
			//window.setTimeout(function (){kt.item(i).focus();}, 0);
			setTimeout(function() { kt.item(i).focus() }, 1000);
			return false;
		}
	}

	var tai_khoan = document.getElementById("fullname");
	if(tai_khoan.value.length < 6)
	{
		Swal.fire("Error", "Tên phải có ít nhất 6 ký tự", "error");
		setTimeout(function() { tai_khoan }, 1000);
		return false;
	}

	if(tai_khoan.value.match(/[^a-zA-Z0-9]/))
	{
		Swal.fire("Error", "Tên không được đặt các ký tự đặc biệt nha!", "error");
		setTimeout(function() { tai_khoan }, 1000);
		return false;
	}

	var email = document.getElementById("email");
	if(!isEmail(email.value)){
		Swal.fire("Error", "Vui lòng nhập email chính xác", "error");
		return false;
	}

	/*var dien_thoai = document.getElementById("dien_thoai");
	if(isNaN(dien_thoai.value)) //nếu nó là chuỗi => sai
	{
		Swal.fire("Error", "Vui lòng nhập số điện thoại thực tế", "error");
		setTimeout(function() { dien_thoai }, 1000);
		return false;
	}

	var t_and_c = document.getElementById("t_and_c");
	if(!(t_and_c.checked))
	{
		Swal.fire("Error", "Bạn chưa đồng ý với điều khoản kìa!", "error");
		setTimeout(function() { t_and_c }, 1000);
		return false;
	}*/

	return false;
}

function isEmail(email) 
{   
	var isValid = false;   
	var regex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;   
	if(regex.test(email)) {   
		isValid = true;   
	}   
	return isValid;   
} 