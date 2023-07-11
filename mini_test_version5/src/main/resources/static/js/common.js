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


