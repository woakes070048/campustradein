
// custom js files

/*

1. Customized navigation effect on scrolling
2. backstrech image flipper header section. see documentation ( http://srobbin.com/jquery-plugins/backstretch/ )
3. activator on sign up or login form. when clicks it will activate the current button
4. Featured Books Carousel. see documentation ( http://www.owlgraphic.com/owlcarousel/ )
5. Swing Scroll to top. Used for the arrow on the right side, which takes you up to the top
6. register and login link auto clicked while opening modal
*/

/* 1. navigation scroll effect */

var navigation = $('.header').offset().top + 200;
var navigation2 =$('.header').offset().top + 20;
$(document).scroll(function(){
	if($(this).scrollTop() > navigation){
		$('.ArrowUp').show();

	}
	else{
		$('.ArrowUp').hide();
	}
	if($(this).scrollTop() > navigation2){
		$('.navbar-default').hide();
	}
	else{
		$('.navbar-default').show();
	}
});

/* 2. backstretch image flipper */

// $(".header").backstretch([
//  "assets/header/1.jpg",
//  "assets/header/2.jpg",
//  "assets/header/3.jpg"
//  ], {
//    fade: 750,
//    duration: 4000
//  });

/* 3. activator */

$('#activator li').on('click', function(){
	$(this).addClass('itemActive').siblings().removeClass('itemActive');
});


var owl = $("#owl-demo");
// Custom Navigation Events
$(".next").click(function(){
	owl.trigger('owl.next');
})
$(".prev").click(function(){
	owl.trigger('owl.prev');
})

/* 4. owl carousel */

owl.owlCarousel({

	// Most important owl features
	items : 4,

	//Autoplay
	autoPlay : true,
	stopOnHover : false,

	// Navigation
	navigation : false,
	rewindNav : true,
	scrollPerPage : false,

	// Responsive
	responsive: true,
	responsiveRefreshRate : 200,
	responsiveBaseWidth: window,

	//Pagination
	pagination : false,
	paginationNumbers: false,

});

// 5. navigation scroll/swing code you can customize it . see documentation for furhter details

$(".scroll").on('click',function(event){
	event.preventDefault();
	//calculate destination place
	var dest=0;
	if($(this.hash).offset().top > $(document).height()-$(window).height()){
		dest=$(document).height()-$(window).height();
	}else{
		dest=$(this.hash).offset().top - 50;
	}
	//go to destination
	$('html,body').animate({scrollTop:dest}, 1000,'swing');
});

// 6. Auto clicked

$('#registerUser').on('click', function(){
	$('#targetRegisterUser').trigger('click');
});
$('#loginUser').on('click', function(){
	$('#targetLogInUser').trigger('click');
});



// 8. login/signup tooltip

$('#targetLogInUser').on('click',function(){
	$('#tooltip').css({
		'margin-left':'120px'
	});
});

$('#targetRegisterUser').on('click',function(){
	$('#tooltip').css({
		'margin-left':'250px'
	});
});

// 9. wow js plugin

new WOW().init();


// 10. vimeo video player control

$('#player1Close').on('click', function(){
	$('#player1').attr('src','');
});

$('.modalSeeVideoButton').on('click', function(){
	// $('#player1').attr('src','http://player.vimeo.com/video/27855315?api=1&player_id=player1&autoplay=1');
	$('#player1').attr('src','https://www.youtube.com/embed/FVKzaKwnYFs?autoplay=1');
});

$('#myModalVideo').on('click', function(){
	$('#player1').attr('src','');
});
