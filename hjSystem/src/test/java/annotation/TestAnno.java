package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.sl.ue.entity.Testa;
import com.sl.ue.entity.User;
import com.sl.ue.util.HumpCrossUnderline;
import com.sl.ue.util.Table;

public class TestAnno {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Annotation[]  anno = User.class.getAnnotations();
		for(Annotation a :anno){
			if(a.annotationType() == Table.class){
				System.out.println("Yes");
				Table table = (Table) a;
				System.out.println(table.value());
				System.out.println(table.enable());
			}else{
				System.out.println("nno");
			}
			System.out.println(a.annotationType());
		}
		User user = new User();
		Testa aa = new Testa();
		Table  table1 = aa.getClass().getAnnotation(Table.class);
		System.out.println(table1);
		
		Field[] fields = user.getClass().getDeclaredFields();
		for(Field field : fields){
			String sss ="gas_zcada_zzz_x_b";
			String newField = HumpCrossUnderline.underlineToHump(sss);
			System.out.println(newField);
		}
		
	}

}
