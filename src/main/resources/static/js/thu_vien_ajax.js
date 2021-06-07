/*Thư viện ajax*/
$(function () //phải có dòng khai báo này trong jquery
{
	$('#tai_khoan').keyup(function()
	{
		var tk = $(this).val();
		$.ajax({
			type : 'POST',
			url : 'xl_dk_tai_khoan_trung.php',
			data : 'tk=' + tk,
			success: function(dataBack)
			{
				$('#ajax-tk-trung').html(dataBack);
			}
		});
	}); //end taikhoan

	$('#btn-sort-gia').click(function()
	{
		var nsx;
		var type_sort = $('#sort-gia').val();
		var url = $(location).attr('href');
		if(url.search('nsx') != -1) //trả về vị trí hoặc -1
		{
			nsx = url.split("=")[1];
		}
		else
		{
			nsx = 0;
		}

		// alert(type_sort);
		$.ajax({
			type : 'POST',
			url : 'xl_sap_xep_gia.php',
			data : {'type' : type_sort, 'nsx' : nsx},
			success: function(dataBack)
			{
				$('#div_sap_xep').html(dataBack);
			}
		})
	}); // end sort-gia

	$('.loc-gia-ne').click(function()
	{
		var type_gia = $(this).attr('id');
		var nsx;
		var url = $(location).attr('href');
		if(url.search('nsx') != -1) //trả về vị trí hoặc -1
		{
			nsx = url.split("=")[1];
		}
		else
		{
			nsx = 0;
		}

		$.ajax({
			type : 'POST',
			url : 'xl_loc_gia.php',
			data : {'type' : type_gia, 'nsx' : nsx},
			success: function(dataBack)
			{
				$('#div_sap_xep').html(dataBack);
			}
		})
	})
}); //end function main
