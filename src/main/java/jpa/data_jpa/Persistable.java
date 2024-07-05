package jpa.data_jpa;

//Id 사용 직접 할당 -> save 호출 -> merge 호출 : db에 값 확인, 없을 경우 새 엔티티로 인지
public interface Persistable<ID> {
    ID getId();
    boolean isNew();
}
