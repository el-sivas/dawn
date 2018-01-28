package de.elsivas.persist;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionDiffBuilder;

/**
 * Persistable Bean
 *
 */

@Entity
public abstract class EntityBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private LocalDateTime validUntil;

	private String systemLabel;

	private final LocalDateTime created = LocalDateTime.now();

	public LocalDateTime getValidUntil() {
		return validUntil;
	}

	public Long getId() {
		return id;
	}

	public boolean isValid() {
		return getValidUntil().isAfter(LocalDateTime.now());
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public String getSystemLabel() {
		return systemLabel;
	}

	public void setSystemLabel(final String systemLabel) {
		this.systemLabel = StringUtils.abbreviate(systemLabel, 35);
	}

	/**
	 * Used by {@link AbstractDaoImpl} over Reflection
	 */
	@SuppressWarnings("unused")
	private void setValidUntil(final LocalDateTime validUntil) {
		this.validUntil = validUntil;
	}

	public final String toStringTechnical() {
		return this.getClass().getSimpleName() + " (" + this.getId() + ") " + this.getSystemLabel();
	}

}
