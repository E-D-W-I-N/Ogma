<#import "parts/common.ftl" as c>

<@c.page>
    <h2>Welcome to Ogma</h2>
    <div class="bd-example" style="width: 50%; margin: auto;">
        <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
                <li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="/static/carousel/books.jpg" class="d-block w-100" alt="products">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>Книги</h5>
                        <p>Можество книг от любых авторов</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="/static/carousel/office.jpg" class="d-block w-100" alt="office">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>Канцтовары</h5>
                        <p>Карандаши, ручки и все что угодно</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="/static/carousel/gift.jpg" class="d-block w-100" alt="gifts">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>Сувениры</h5>
                        <p>Подарки, сувениры, настольные игры</p>
                    </div>
                </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
    <div>
        <a href="/login" class="btn btn-primary">Войти</a>
        <a href="/registration" class="btn btn-primary">Зарегистрироваться</a>
    </div>
</@c.page>