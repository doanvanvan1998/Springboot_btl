function showListMenu(){
    let menu = document.querySelector('.menu_mobile-list');
    if( menu.style.display === "block"){
        $(".menu_mobile-list").slideUp(500,function(){});
    }else{
        $(".menu_mobile-list").slideDown(500,function(){});
    }
}
function showListNav(){
    let menu = document.querySelector('.nav_mobile-list');
    if( menu.style.display === "block"){
        $(".nav_mobile-list").slideUp(500,function(){});
    }else{
        $(".nav_mobile-list").slideDown(500,function(){});
    }
}
function displayGridView(){
    document.querySelector(".grid_view").style.display = "flex";
    document.querySelector(".list_view").style.display = "none";
    document.getElementById("display_gridview").classList.add("active");
    document.getElementById("display_listview").classList.remove("active");
}
function displayListView(){
    document.querySelector(".grid_view").style.display = "none";
    document.querySelector(".list_view").style.display = "flex";
    document.getElementById("display_gridview").classList.remove("active");
    document.getElementById("display_listview").classList.add("active");
}
$(".filter_mobile-title").hover(()=>{
    let filter_mobile = document.querySelector('.filter_mobile-main');
    if( filter_mobile.style.display === "block"){
        filter_mobile.style.display = "none";
    }else{
        filter_mobile.style.display = "block";
    }
})
// Register
$("#confirm_register").click(function(e){
    let userName = $("#userName").val();
    let password = $("#password").val();
    let confirm_password = $("#confirm_password").val();
    let phone = $("#phone").val();
    let vnf_regex = /^(0[2-9][0-9]{8})$/g;
    if( userName.length < 6 ){
        $("#username_error").html("Username 6 characters minimum");
        return false;
    }else $("#username_error").html("");
    if( password.length < 6 ){
        $("#password_error").html("Password 6 characters minimum");
        return false;
    }else $("#password_error").html("");
    if( confirm_password !== password ){
        $("#confirm_password_error").html("Password confirm is incorrect");
        return false;
    }else $("#confirm_password_error").html("");
    if (vnf_regex.test(phone) == false){
        $("#phone_error").html("Phone number is not in the correct format");
        return false;
    }else   $("#phone_error").html("");

    e.preventDefault(); // ????y l?? ???????ng d???n ?????n api. N???u kh??ng c?? ???????ng d???n n??y n?? s??? request v??o link ??ang ?????ng hi???n t???i
    // Khi data n???m ngo??i client th?? ki???u d??? d??? li???u c???a n?? s??? l?? javascript Object
    const data = {};
    //$('#formSubmit').serializeArray(); d??ng ????? l???y t???t c??? c??c name c???a nh???ng field trong form
    let formData = $('#form_register').serializeArray();
    $.each(formData, function(i, v){
        data[""+v.name+""] = v.value;
    });
    addUser(data);
})
//Add user when register
function addUser(data){
    $.ajax({
        url: '/register',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: result=>{
            if( result.userName != null ){
                $('.login').css('position','static')
                showNotificationRegister(result);
            }else{
                $('#username_error').html("Username already exists");

            }
            console.log(result);
        },
        error: result=>{
            alert(result);
        }
    });
}

function showNotificationRegister(result){
    swal({
        title: "Inform",
        text: "Register success",
        type: "success",
        showCancelButton: true,
        cancelButtonClass: "btn-success",
        confirmButtonText: "Login now",
        cancelButtonText: "????ng",
    }).then(function(isConfirm) {
        if (isConfirm.dismiss != "cancel") {
            window.location.assign("/login?userName="+result.userName);
        }
    });
};
// View quick and add to cart quick
function displayAction(objectId) {
    document.getElementById("action"+objectId).style.animation = "displayAction 1s forwards";
}

function hiddenAction(objectId){
    document.getElementById("action"+objectId).style.animation = "hiddenAction 1s forwards";
}

function display_viewquick(objectId){
    $.ajax({
        url: '/quick_view_info/'+objectId,
        success: result => {
            $("#quick_view").html(result);
        },
        error: function(result){
            console.log(result);
            alert("error");
        }
    });
    $('#quick_view').css("display","block");
}

function addtocart_quick(objectId){
    $.ajax({
        url: '/cart/add?id='+objectId,
        success: result => {
            let quantity = 0;
            $(result.listOrderDetail).each(function (i,orderDetail){
                quantity += orderDetail.quantity;
            })
            $('.cart-popup-count').html(quantity);
            showNotificationAdded();
        },
        error: function(result){
            console.log(result);
            alert("error");
        }
    });
}
function showNotificationAdded(){
    swal({
        title: "Th??ng b??o",
        text: "???? th??m v??o gi??? h??ng",
        type: "success",
        cancelButtonClass: "btn-success",
        cancelButtonText: "????ng",
    }).then(function(isConfirm) {
    });
};
// payment
function changeQuantity(objectId, quantity){
    $.ajax({
        url: '/cart/update?id='+objectId+'&quantity='+quantity,
        success: result => {
            console.log(result);
            let quatity = 0;
            $(result.listOrderDetail).each(function (i,product){
                if( product.foodDTO.id == objectId ) {
                    $('#subtotal_'+objectId).html(product.subTotal);
                    console.log(product.subTotal);
                }
                quatity += product.quantity;
            })
            $('.cart-popup-count').html(quatity);
            $("#total").html(result.total);
            console.log(result.total);
        },
        error: result => {
            console.log(result);
            alert("Error");
        }
    });
}
function deleteObject(objectId){
    $.ajax({
        url: '/cart/remove?id='+objectId,
        success: result => {
            console.log(result);
            let quatity = 0;
            $(result.listOrderDetail).each(function (i,product){
                if( product.foodDTO.id == objectId ) {
                    $('#subtotal_'+objectId).html(product.subTotal);
                    console.log(product.subTotal);
                }
                quatity += product.quantity;
            })
            $('.cart-popup-count').html(quatity);
            $("#total").html(result.total);
            console.log(result.total);
            $("#"+objectId).css("display","none");
            if( quatity == 0 ){
                $('#pay').css("pointer-events","none");
            }
        },
        error: result=>{
            console.log(result);
            alert("Kh??ng ???????c");
        }
    });
}
// Contact
$('#btnSubmit_contact').click(function (e){
    e.preventDefault(); // ????y l?? ???????ng d???n ?????n api. N???u kh??ng c?? ???????ng d???n n??y n?? s??? request v??o link ??ang ?????ng hi???n t???i
    // Khi data n???m ngo??i client th?? ki???u d??? d??? li???u c???a n?? s??? l?? javascript Object
    const data = {};
    //$('#formSubmit').serializeArray(); d??ng ????? l???y t???t c??? c??c name c???a nh???ng field trong form
    let formData = $('#form_contact').serializeArray();
    $.each(formData, function(i, v){
        data[""+v.name+""] = v.value;
    });
    addContact(data);
})

function addContact(data){
    $.ajax({
        url: '/contact',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: result=>{
            if( result.fullName != null ){
                showNotificationRegister();
                console.log(result);
            }else{
                alert("Error")
                console.log(result);
            }
        },
        error: result=>{
            alert(result);
            console.log(result);
        }
    });
}

function showNotificationRegister(){
    swal({
        title: "Inform",
        text: "Send success",
        type: "success",
        confirmButtonClass: "btn-success",
        confirmButtonText: "????ng",
    }).then(function(isConfirm) {
    });
};