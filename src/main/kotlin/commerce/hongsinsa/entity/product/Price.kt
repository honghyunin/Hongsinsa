package commerce.hongsinsa.entity.product

enum class Price(val title: String, val maxPrice: Int, val minPrice: Int) {


    ALL("전체", 0, 999999999),
    PRICE1("~20000", 20000, 0),
    PRICE2("20000~40000", 40000, 20000),
    PRICE3("40000~60000", 60000, 40000),
    PRICE4("60000~90000", 90000, 60000)
}