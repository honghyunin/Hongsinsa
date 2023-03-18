package commerce.hosinsa.entity.order

enum class OrderStatus(val status: String) {
    ORDER_RECEIVED("주문 접수"),
    ORDER_CANCEL("주문 취소"),
    IN_TRANSIT("배송 중"),
    DELIVERED("배송 완료")
}
