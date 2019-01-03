package org.github.unicon;

import org.github.unicon.conv.measure.impl.LengthUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UniconApplicationTests {

	@Test
	public void contextLoads() {
		final BigDecimal source = BigDecimal.valueOf(150);
		final BigDecimal result = LengthUnit.METER.convert(source, LengthUnit.KILOMETER);

		Assert.assertEquals(BigDecimal.valueOf(0.150), result);
	}

}

