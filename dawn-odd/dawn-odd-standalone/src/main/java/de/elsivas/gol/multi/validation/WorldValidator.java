package de.elsivas.gol.multi.validation;

import de.elsivas.basic.ValidationException;
import de.elsivas.gol.multi.RichParcelContent;

public class WorldValidator {
	
	public static void validate(RichParcelContent[][] world) {
		int length = world.length;
		for (RichParcelContent[] richParcelContents : world) {
			if(richParcelContents.length != length) {
				throw new ValidationException("length");
			}
		}
		
	}

}
