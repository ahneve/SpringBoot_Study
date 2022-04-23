//일기 엔티티 클래스
package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="DIARY")
@Getter @Setter
public class ae_01 {


    @Id
    @GeneratedValue
    private Long diary_id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    //private User user;

    @Column(name="IMAGE_URL")
    private String image;

    @Column(name="SOURCE_AUDIO_URL")
    private String source_audio;

    private String title;

    private String text;

    private String server_date;

    private String user_date;



}
