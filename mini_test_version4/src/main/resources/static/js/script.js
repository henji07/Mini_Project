// window.onload = function () {
//     const menuItems = document.querySelectorAll(".profile-menu1, .menu2, .menu3");
//     menuItems[0].classList.add("active"); // Set the default underline on menu1

//     menuItems.forEach((item) => {
//       item.addEventListener("click", function () {
//         menuItems.forEach((i) => i.classList.remove("active")); // Remove underline from all menu items
//         item.classList.add("active"); // Add underline to the clicked item
//       });
//     });

//   };

$(document).ready(function () {
    $("#profile-board-form").css("display", "none");
    $("#profile-user-info2").css("display", "none");
    $("#profile-user-info3").css("display", "none");
    $("#profile-write-board2").css("display","none");
    $("#profile-write-board3").css("display","none");
    $("#profile-con2-2").css("display", "none");
    $("#profile-con3-2").css("display", "none");
    var menuItems = $(".profile-menu1, .profile-menu2, .profile-menu3");
    menuItems.eq(0).addClass("profile-active"); // Set the default underline on menu1

    menuItems.click(function () {
        menuItems.removeClass("profile-active"); // Remove underline from all menu items
        $(this).addClass("profile-active"); // Add underline to the clicked item
    });





    $("#profile-changeProfilePicBtn").on("click", function () {
        $("#profile-imageUpload").trigger("click");
    });
    $("#profile-imageUpload").on("change", function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (event) {
                $("#profile-profilePic").attr("src", event.target.result);
            };
            reader.readAsDataURL(file);
        }
    });



    var menuItems2 = $("#profile-sel1, #profile-sel2, #profile-sel3");
    menuItems2.eq(0).addClass("profile-active2"); // Set the default underline on menu1

    menuItems2.click(function () {
        menuItems2.removeClass("profile-active2"); // Remove underline from all menu items
        $(this).addClass("profile-active2"); // Add underline to the clicked item
    });




    $(".profile-menu1").on("click", () => {
        $("#profile-member-form").css("display", "");
        $("#profile-user-info1").css("display", "");
        $("#profile-user-info2").css("display", "none");
        $("#profile-user-info3").css("display", "none");
        $("#profile-con2-2").css("display", "none");
        $("#profile-con3-2").css("display", "none");
    });

    $(".profile-menu2").on("click", () => {
        $("#profile-member-form").css("display", "none");
        $("#profile-user-info1").css("display", "none");
        $("#profile-user-info2").css("display", "");
        $("#profile-user-info3").css("display", "none");
        $("#profile-con2-2").css("display", "");
        $("#profile-con3-2").css("display", "none");
    });

    $(".profile-menu3").on("click", () => {
        $("#profile-member-form").css("display", "none");
        $("#profile-user-info1").css("display", "none");
        $("#profile-user-info2").css("display", "none");
        $("#profile-user-info3").css("display", "");
        $("#profile-con2-2").css("display", "none");
        $("#profile-con3-2").css("display", "");
    });



    $("#profile-sel1").on("click",()=>{
        checkBox();
        $("#profile-write-board2").css("display","none");
        $("#profile-write-board1").css("display","");
        $("#profile-write-board3").css("display","none");
    })
    $("#profile-sel2").on("click",()=>{
        checkBox();
        $("#profile-write-board2").css("display","");
        $("#profile-write-board1").css("display","none");
        $("#profile-write-board3").css("display","none");
    })
    $("#profile-sel3").on("click",()=>{
        checkBox();
        $("#profile-write-board2").css("display","none");
        $("#profile-write-board1").css("display","none");
        $("#profile-write-board3").css("display","");
    })
    const checkBox = ()=>{
        $("#profile-select-all-board").prop("checked", false);
        $('input[name="board-check"]').prop('checked', false);
    }
    $("#profile-interests").select2({
        placeholder: "관심사를 선택하세요",
        allowClear: true,
    });

    var phoneRegex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

    $('input[name="phone"]').on("input", function () {
        var phone = $(this).val();
        if (!phoneRegex.test(phone)) {
            $(this).next(".profile-hidden").show();
        } else {
            $(this).next(".profile-hidden").hide();
        }
    });

    $("#profile-memberForm").on("submit", function (e) {
        e.preventDefault();
        // 여기에 폼 제출 로직을 추가하실 수 있습니다.
    });


    var $checkAll = $('#profile-select-all-board');
    $checkAll.change(function () {
        var $this = $(this);
        var checked = $this.prop('checked'); // checked 문자열 참조(true, false)
        // console.log(checked);
        $('input[name="board-check"]').prop('checked', checked);
    });

    var slideIndex = 0;
      var dots = document.querySelectorAll(".profile-dot");
      var slides = document.querySelectorAll(".profile-slide");
      var slider = document.querySelector(".profile-slider");

      showSlide(slideIndex);
      setInterval(nextSlide, 3000);

      function showSlide(n) {
        slideIndex = n;
        var translateXValue = -slideIndex * (100 / slides.length) + "%";
        slider.style.transform = "translateX(" + translateXValue + ")";
        for (var i = 0; i < dots.length; i++) {
          dots[i].classList.remove("profile-active");
        }
        dots[slideIndex].classList.add("profile-active");
      }

      function nextSlide() {
        slideIndex++;
        if (slideIndex === slides.length) {
          slideIndex = 0;
        }
        showSlide(slideIndex);
      }

      function prevSlide() {
        slideIndex--;
        if (slideIndex < 0) {
          slideIndex = slides.length - 1;
        }
        showSlide(slideIndex);
      }

      function setSlide(n) {
        slideIndex = n;
        showSlide(slideIndex);
      }

      for (var i = 0; i < dots.length; i++) {
        dots[i].addEventListener("click", function () {
          var index = Array.prototype.indexOf.call(dots, this);
          setSlide(index);
        });
      }

});