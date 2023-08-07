package commerce.hongsinsa.entity.request

enum class RequestStatus(val status: String) {
    Request_RECEIVED("주문 접수"),
    Request_CANCEL("주문 취소"),
    IN_TRANSIT("배송 중"),
    DELIVERED("배송 완료")
}
