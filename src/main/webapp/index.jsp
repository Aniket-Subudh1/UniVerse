<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
            href="https://cdn.jsdelivr.net/npm/remixicon@4.0.0/fonts/remixicon.css"
            rel="stylesheet"
    />
    <link rel="stylesheet" href="styles/home.css" />
    <title>Collage Management System | UniVerse</title>
</head>
<body>
<header class="header">
    <nav>
        <div class="nav__bar">
            <div class="logo">
                <a href="#"><img src="styles/img/assets/logo.png" alt="logo" /></a>
            </div>
            <div class="nav__menu__btn" id="menu-btn">
                <i class="ri-menu-line"></i>
            </div>
        </div>
        <ul class="nav__links" id="nav-links">
            <li><a href="#home">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#service">Services</a></li>
            <li><a href="#explore">Explore</a></li>
            <li><a href="#contact">Contact</a></li>
        </ul>
        <a class="btn nav__btn" href="home.jsp" >Login/SignUp</a>
    </nav>
    <div class="section__container header__container" id="home">
        <p>Simple - Unique - UserFriendly</p>
        <h1> Best CMS Offered <br />In Our <span>UniVerse</span>.</h1>
    </div>
</header>

<section class="section__container booking__container">
    <form action="/" class="booking__form">
        <div class="input__group">
            <span><i class="ri-calendar-2-fill"></i></span>
            <div>
                <label>TEACHER REGISTRATION</label>
                <input type="text" placeholder="Teacher Registration" />
            </div>
        </div>
        <div class="input__group">
            <span><i class="ri-calendar-2-fill"></i></span>
            <div>
                <label>STUDENT REGISTRATION</label>
                <input type="text" placeholder="Student Registration" />
            </div>
        </div>
        <div class="input__group">
            <span><i class="ri-user-fill"></i></span>
            <div>
                <label>USER</label>
                <input type="text" placeholder="User" />
            </div>
        </div>
        <div class="input__group input__btn">
            <a class="btn" href="home.jsp">LOGIN</a>
        </div>

    </form>
</section>

<section class="section__container about__container" id="about">
    <div class="about__image">
        <img src="styles/img/assets/about.webp" alt="about" />
    </div>
    <div class="about__content">
        <p class="section__subheader">ABOUT US</p>
        <h2 class="section__header">The Best CMS is here!</h2>
        <p class="section__description">
           ERP is a type of software that connects day-to-day business processes, including inventory and order management, supply chain, accounting, human resources, and customer relationship management. ERP software can help streamline business processes and improve efficiency.
        </p>
        <div class="about__btn">
            <button class="btn">Read More</button>
        </div>
    </div>
</section>

<section class="section__container room__container">
    <p class="section__subheader">OUR SERVICES</p>
    <h2 class="section__header">The Most Secured ERP.</h2>
    <div class="room__grid">
        <div class="room__card">
            <div class="room__card__image">
                <img src="styles/img/assets/room-1.png" alt="room" />
                <div class="room__card__icons">
                    <span><i class="ri-heart-fill"></i></span>
                    <span><i class="ri-paint-fill"></i></span>
                    <span><i class="ri-shield-star-line"></i></span>
                </div>
            </div>
            <div class="room__card__details">
                <h4>Student Information & Academic Records</h4>
                <p>
                    Role-based access control to ensure only authorized staff and faculty can view or edit student data.
                </p>
                <h5>Starting from <span>$99/month</span></h5>
                <button class="btn">CHECK</button>
            </div>
        </div>
        <div class="room__card">
            <div class="room__card__image">
                <img src="styles/img/assets/room-2.webp" alt="room" />
                <div class="room__card__icons">
                    <span><i class="ri-heart-fill"></i></span>
                    <span><i class="ri-paint-fill"></i></span>
                    <span><i class="ri-shield-star-line"></i></span>
                </div>
            </div>
            <div class="room__card__details">
                <h4>Automated Attendance Tracking & Analysis</h4>
                <p>
                    Biometric and RFID integration options for secure, accurate attendance logging with real-time dashboard analytics.
                </p>
                <h5>Starting from <span>$49/month</span></h5>
                <button class="btn">CHECK</button>
            </div>
        </div>
        <div class="room__card">
            <div class="room__card__image">
                <img src="styles/img/assets/room-3.png" alt="room" />
                <div class="room__card__icons">
                    <span><i class="ri-heart-fill"></i></span>
                    <span><i class="ri-paint-fill"></i></span>
                    <span><i class="ri-shield-star-line"></i></span>
                </div>
            </div>
            <div class="room__card__details">
                <h4>Financial Transactions Security</h4>
                <p>
                    Integrated online payment gateway with fraud detection and transaction logging for compliance.
                </p>
                <h5>Starting from <span>$59/month</span></h5>
                <button class="btn">CHECK</button>
            </div>
        </div>
    </div>
</section>

<section class="service" id="service">
    <div class="section__container service__container">
        <div class="service__content">
            <p class="section__subheader">SERVICES</p>
            <h2 class="section__header">Strive Only For The Best.</h2>
            <ul class="service__list">
                <li>
                    <span><i class="ri-shield-star-line"></i></span>
                    High Class Security
                </li>
                <li>
                    <span><i class="ri-24-hours-line"></i></span>
                    24 Hours Service
                </li>
                <li>
                    <span><i class="ri-headphone-line"></i></span>
                    Customer Support
                </li>
                <li>
                    <span><i class="ri-map-2-line"></i></span>
                     Guide & User Support
                </li>
            </ul>
        </div>
    </div>
</section>

<section class="section__container banner__container">
    <div class="banner__content">
        <div class="banner__card">
            <h4>500+</h4>
            <p>Courses Offered</p>
        </div>
        <div class="banner__card">
            <h4>1000+</h4>
            <p>Active Users</p>
        </div>
        <div class="banner__card">
            <h4>99.9%</h4>
            <p>Uptime Guarantee</p>
        </div>
    </div>
</section>

<section class="explore" id="explore">
    <p class="section__subheader">EXPLORE</p>
    <h2 class="section__header">What's New Today.</h2>
    <div class="explore__bg">
        <div class="explore__content">
            <p class="section__description">NOV 2024</p>
            <h4>New Feature Release - Enhanced Student Dashboard</h4>
            <button class="btn">Explore New Features</button>
        </div>
    </div>
</section>

<footer class="footer" id="contact">
    <div class="section__container footer__container">
        <div class="footer__col">
            <div class="logo">
                <a href="#home"><img src="styles/img/assets/logo.png" alt="logo" /></a>
            </div>
            <p class="section__description">
                Discover a secure and efficient ERP solution designed to streamline academic, administrative, and financial operations across our college.
            </p>
            <button class="btn">Book Now</button>
        </div>
        <div class="footer__col">
            <h4>QUICK LINKS</h4>
            <ul class="footer__links">
                <li><a href="#">Academic Calendar</a></li>
                <li><a href="#">Course Registration</a></li>
                <li><a href="#">Exam Schedules</a></li>
                <li><a href="#">Student Support Services</a></li>
                <li><a href="#">Library Resources</a></li>
            </ul>
        </div>
        <div class="footer__col">
            <h4>OUR SERVICES</h4>
            <ul class="footer__links">
                <li><a href="#">Student Portal Access</a></li>
                <li><a href="#">Faculty & Staff Dashboard</a></li>
                <li><a href="#">Secure Payment Processing</a></li>
                <li><a href="#">Attendance & Grading System</a></li>
            </ul>
        </div>
        <div class="footer__col">
            <h4>CONTACT US</h4>
            <ul class="footer__links">
                <li><a href="#">universe@info.com</a></li>
            </ul>
            <div class="footer__socials">
                <a href="#"><img src="styles/img/assets/facebook.png" alt="facebook" /></a>
                <a href="#"><img src="styles/img/assets/instagram.png" alt="instagram" /></a>
                <a href="#"><img src="styles/img/assets/youtube.png" alt="youtube" /></a>
                <a href="#"><img src="styles/img/assets/twitter.png" alt="twitter" /></a>
            </div>
        </div>
    </div>
    <div class="footer__bar">
        Copyright © 2024 UniVerse. All rights reserved.
    </div>
</footer>

<script src="https://unpkg.com/scrollreveal"></script>
<script src="script/home.js"></script>
</body>
</html>