package com.ibm.telco.som;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="payload")
public class SomPayloadEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    public String payloadKey;
    public String payload;

    public SomPayloadEntity(String payloadKey, String payload) {
        this.payloadKey = payloadKey;
        this.payload = payload;
    }


    @Override
    public String toString()
    {
        return String.format(
                "SomPayload[key='%s', order='%s']",
                payloadKey, payload);
    }
}
