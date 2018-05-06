import de.elsivas.basic.ESOptions;

public class TestApplication {

	public static void main(String... args) {
		ESOptions.defaultAndHandleHelp(args, TestApplication.class.getSimpleName());
		// final Option c = o.getOption("c");
		// final String value = c.getValue(0);
		// try {
		// BasicConfig.setConfigFile(value);
		// } catch (final EsLogicException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// System.out.println("OK!");
		// System.exit(0);
	}
	//
}
