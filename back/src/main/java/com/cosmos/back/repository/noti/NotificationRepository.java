package com.cosmos.back.repository.noti;

import com.cosmos.back.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * 유저가 받은 알림 리스트
     */
    @Query("select n from Notification n " +
            "where n.receiver.userSeq = :userSeq " +
            "order by n.id desc")
    List<Notification> findAllByUserSeq(@Param("userSeq") Long userSeq);

    /**
     * 안읽은 알림 개수
     */
    @Query("select count(n) from Notification n " +
            "where n.receiver.userSeq = :userSeq and " +
            "n.isRead = false")
    Long countUnReadNotifications(@Param("userSeq") Long userSeq);

    /**
     * 읽은 알림 개수
     */
    @Query("select count(n) from Notification n " +
            "where n.receiver.userSeq = :userSeq and " +
            "n.isRead = true")
    Long countReadNotifications(@Param("userSeq") Long userSeq);

    void deleteById(Long notiId);

    @Modifying
    @Transactional
    @Query("delete from Notification n " +
            "where n.receiver.userSeq = :userSeq")
    void deleteByReceiver(@Param("userSeq") Long userSeq);
}
