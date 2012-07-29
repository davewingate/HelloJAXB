package hello.jaxb.java2xsd;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@XmlType
public class Salutation
{
	@XmlValue
	private final String salutation;

	private final String language;

	public Salutation()
	{
		this("Hello");
	}

	public Salutation(String salutation)
	{
		super();
		this.salutation = salutation;
		this.language = "English";
	}

	public String getSalutation()
	{
		return salutation;
	}

	public String getLanguage()
	{
		return language;
	}

	@Override
	public String toString()
	{
		return salutation;
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object anotherObj)
	{
		return EqualsBuilder.reflectionEquals(this, anotherObj);
	}

}
