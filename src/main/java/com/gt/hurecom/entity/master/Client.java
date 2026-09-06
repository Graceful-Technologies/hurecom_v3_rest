package com.gt.hurecom.entity.master;

import com.gt.hurecom.entity.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "clients", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Client extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String name;

	@Column(name = "is_active", nullable = false)
	private boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
