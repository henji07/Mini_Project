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
<<<<<<< HEAD
    $("#profile-write-board2").css("display","none");
    $("#profile-write-board3").css("display","none");
    $("#profile-con2-2").css("display", "none");
    $("#profile-con3-2").css("display", "none");
=======
    $("#profile-write-board2").css("display", "none");
    $("#profile-write-board3").css("display", "none");
    $("#profile-con2-2").css("display", "none");
    $("#profile-con3-2").css("display", "none");
    $(".pagination-bottom-pages2").css("display", "none");
    $(".pagination-bottom-pages3").css("display", "none");

>>>>>>> origin/newyoojin
    var menuItems = $(".profile-menu1, .profile-menu2, .profile-menu3");
    menuItems.eq(0).addClass("profile-active"); // Set the default underline on menu1

    menuItems.click(function () {
        menuItems.removeClass("profile-active"); // Remove underline from all menu items
        $(this).addClass("profile-active"); // Add underline to the clicked item
    });



<<<<<<< HEAD


=======
>>>>>>> origin/newyoojin
    $("#profile-changeProfilePicBtn").on("click", function () {
        $("#profile-imageUpload").trigger("click");
    });
    $("#profile-imageUpload").on("change", function () {
<<<<<<< HEAD
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (event) {
                $("#profile-profilePic").attr("src", event.target.result);
            };
            reader.readAsDataURL(file);
=======
        if (confirm("저장하시겠습니까?")) {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (event) {
                    $(".profile-img").attr("src", event.target.result);

                    // Create new FormData instance
                    let formData = new FormData();

                    // Add the file to the form data
                    formData.append("image", file); // 'image'는 서버에서 요구하는 파라미터 이름입니다.

                    // Send the file data to the server using AJAX
                    $.ajax({
                        type: "POST",
                        url: "/changeImg", // 여기에 실제 서버의 엔드포인트를 넣어야 합니다.
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function (response) {
                            console.log(response);
                            // 추가적인 성공 처리 로직
                        },
                        error: function (jqXHR, textStatus, errorMessage) {
                            console.log(errorMessage);
                            // 추가적인 에러 처리 로직
                        }
                    });
                };
                reader.readAsDataURL(file);
            } else return;

>>>>>>> origin/newyoojin
        }
    });


<<<<<<< HEAD

=======
>>>>>>> origin/newyoojin
    var menuItems2 = $("#profile-sel1, #profile-sel2, #profile-sel3");
    menuItems2.eq(0).addClass("profile-active2"); // Set the default underline on menu1

    menuItems2.click(function () {
        menuItems2.removeClass("profile-active2"); // Remove underline from all menu items
        $(this).addClass("profile-active2"); // Add underline to the clicked item
    });

<<<<<<< HEAD



=======
>>>>>>> origin/newyoojin
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


<<<<<<< HEAD

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
=======
    $("#profile-sel1").on("click", () => {
        checkBox();
        $("#profile-write-board2").css("display", "none");
        $("#profile-write-board1").css("display", "");
        $("#profile-write-board3").css("display", "none");
        $(".pagination-bottom-pages1").css("display", "");
        $(".pagination-bottom-pages2").css("display", "none");
        $(".pagination-bottom-pages3").css("display", "none");
        $("#history-cate").val("board");
    })
    $("#profile-sel2").on("click", () => {
        checkBox();
        $("#profile-write-board2").css("display", "");
        $("#profile-write-board1").css("display", "none");
        $("#profile-write-board3").css("display", "none");
        $(".pagination-bottom-pages1").css("display", "none");
        $(".pagination-bottom-pages2").css("display", "");
        $(".pagination-bottom-pages3").css("display", "none");
        $("#history-cate").val("comm");
    })
    $("#profile-sel3").on("click", () => {
        checkBox();
        $("#profile-write-board2").css("display", "none");
        $("#profile-write-board1").css("display", "none");
        $("#profile-write-board3").css("display", "");
        $(".pagination-bottom-pages1").css("display", "none");
        $(".pagination-bottom-pages2").css("display", "none");
        $(".pagination-bottom-pages3").css("display", "");
        $("#history-cate").val("like");
    })
    const checkBox = () => {
>>>>>>> origin/newyoojin
        $("#profile-select-all-board").prop("checked", false);
        $('input[name="board-check"]').prop('checked', false);
    }
    $("#profile-interests").select2({
        placeholder: "관심사를 선택하세요",
        allowClear: true,
<<<<<<< HEAD
    });

    var phoneRegex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

    $('input[name="phone"]').on("input", function () {
=======
    }).trigger('change');

    var phoneRegex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

    $('input[name="phone"]').on("blur", function () {

>>>>>>> origin/newyoojin
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
<<<<<<< HEAD
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
=======
    var dots = document.querySelectorAll(".profile-dot");
    var slides = document.querySelectorAll(".profile-slide");
    var slider = document.querySelector(".profile-slider");

>>>>>>> origin/newyoojin

});