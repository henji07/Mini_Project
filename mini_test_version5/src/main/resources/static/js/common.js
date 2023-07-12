/**
 * 공통 스크립트 
 * form을 Object로 바꿔준다. (JSON타입으로)
 */
$.fn.serializeObject = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

<<<<<<< HEAD
// $("#search-button").click(function () {
//     var keyword = $('#search_input').val();
//     console.log(keyword);
//     searchPosts(keyword);
// })
        //검색 게시판 헤더 부분 검색창
        // 검색 버튼 클릭 이벤트 처리
// $(function () {
//
//     function searchPostsMain(keyword) {
//         var url = '/api/searchMainBox?';
//         if (keyword) {
//             url += 'keyword=' + encodeURIComponent(keyword);
//         }
//         $.ajax({
//             url: url,
//             method: 'GET',
//             success: function (response) {
//                 window.location.href = "/api/searchMainBox?keyword=" + encodeURIComponent(keyword);
//             },
//             error: function (error) {
//                 console.error('검색 요청 실패:', error);
//             }
//         });
//
//         $.ajax({
//             url: '/api/searchMainBox',
//             method: 'POST',
//             data: {
//                 keyword: keyword
//             },
//             success: function (response) {
//                 document.write(response);
//                 console.log(response);
//                 window.location.href = response;
//             },
//             error: function (error) {
//                 console.error('검색 요청 실패:', error);
//             }
//         });
//     }
//     function searchPosts(keyword) {
//         // 서버로 검색어 전송 및 결과 받아오는 AJAX 요청
//         $.ajax({
//             url: '/api/search',
//             method: 'POST',
//             data: {keyword: keyword},
//             success: (response) => {
//                 console.log('success', response)
//                 let searchResults = response.results;
//                 console.log(searchResults);
//                 // 테이블 초기화
//                 $('#ulTable > li:nth-child(2)').empty(); // 게시물이 출력될 영역 초기화
//                 console.log(response.item.searchBoardList.length);
//
//                 if (response.item.searchBoardList.length >= 1) {
//                     console.log("if문 들어왔음")
//                     response.item.searchBoardList.forEach(function (item) {
//                         // 게시물을 동적으로 생성하여 추가
//                         var listItem = $('<ul>');
//
//                         var categoryItem = $('<li>').text(item.searchCate);
//                         var titleItem = $('<li>').addClass('left').text(item.searchTitle);
//                         var regDateItem = $('<li>').text(item.searchRegDate);
//                         var writerItem = $('<li>').text(item.searchWriter);
//                         var hitItem = $('<li>').text(item.searchCnt);
//                         console.log(listItem, categoryItem, titleItem, regDateItem, writerItem, hitItem)
//                         listItem.append(categoryItem, titleItem, regDateItem, writerItem, hitItem);
//                         $('#ulTable > li:nth-child(2)').append(listItem);
//                     });
//                 }
//             },
//             error: function (error) {
//                 console.error('검색 요청 실패:', error);
//             }
//         });
//     }
// });
=======
/*이메일 형식 검증*/
	function validateEmail(email) {
	    var re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
	    return re.test(String(email).toLowerCase());
	}
	
/*비밀번호 형식 검증*/
	function patternCheckPassword(password) {
		console.log(password);
	    var re = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/;
	    return re.test(password);
	}

>>>>>>> origin/newyoojin

