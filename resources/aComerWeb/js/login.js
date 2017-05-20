$(function() {

    $('#login-form-link').click(function(e) {
		$("#login-form").delay(100).fadeIn(100);
 		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
 		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
});





$(function() {

    $('#addrestaurante-form-link').click(function(e) {
		$("#addrestaurante-form").delay(100).fadeIn(100);
 		$("#addmenu-form").fadeOut(100);
		$('#addmenu-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	$('#addmenu-form-link').click(function(e) {
		$("#addmenu-form").delay(100).fadeIn(100);
 		$("#addrestaurante-form").fadeOut(100);
		$('#addrestaurante-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
});
