package com.sos.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="forum")
public class Forum {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", updatable=false)
	private Long id;
	
	@OneToMany(mappedBy="forum", fetch=FetchType.EAGER, orphanRemoval=true)
	private Set<Post> posts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Forum other = (Forum) obj;		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		
		return true;
	}
}
