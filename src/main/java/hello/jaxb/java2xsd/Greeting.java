package hello.jaxb.java2xsd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Greeting
{

	private static final AtomicInteger idSequence = new AtomicInteger(1);

	@XmlAttribute
	private final int id;

	@XmlElement(nillable=false, required=true)
	private final Salutation salutation;

	@XmlElementWrapper(name = "recipients", nillable=false, required=true)
	@XmlElement(name = "recipient", required=false)
	private final List<String> recipients;

	public Greeting()
	{
		this(new Salutation(), "World");
	}

	public Greeting(Salutation salutation, String... recipients)
	{
		super();
		this.id = idSequence.getAndIncrement();
		this.salutation = salutation;
		this.recipients = new ArrayList<String>(Arrays.asList(recipients));
	}

	public int getId()
	{
		return id;
	}

	public Salutation getSalutation()
	{
		return salutation;
	}

	public List<String> getRecipients()
	{
		return Collections.unmodifiableList(recipients);
	}

	@Override
	public String toString()
	{
		return id + " = " + salutation + ", " + StringUtils.join(recipients, " & ");
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
