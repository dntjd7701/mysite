
<insert id="insert" parameterType="boardvo">

 	 <choose>
 	 
 	 
 	<when test='groupNo==null'>
		



where title Like #{keyword}

keyword= "%" + keyword + "%";


where title Like '%${keyword}%'는 괜찮음. 치환하고 싶을 때는 '$'를 쓸 수 있음. 
