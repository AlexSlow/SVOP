
var grid = document.getElementById('grid');
var Save_Vector=[0,0];
var Counter=[-1,0];
grid.onclick = function(e) {
      if (e.target.tagName != 'TH') return;
 var Vector;
 
 
 
 
 
 
 var elements =grid.getElementsByTagName('TH');
//
 for (var i=0;i<elements.length;i++)
 {
	 elements[i].style.color="#000000";
 }
 
 if (Counter[0]!=e.target.cellIndex)
 {
	e.target.style.color="#FF5555";
	Counter[0]=e.target.cellIndex;
	Counter[1]=0;
 }else if(Counter[0]==e.target.cellIndex){
	
 if (Counter[1]==1)
	 {
	e.target.style.color="#FF5555";
	Counter[1]=0;
	 }
	 else{
		 Counter[1]=0;
		 e.target.style.color="#5555FF"
		 Counter[1]=1;
	 }
	
 }

 //e.target.style.color="#5555FF";
 
 
 
 
      // Если TH -- сортируем
	  if (Save_Vector[0]==e.target.cellIndex)
	  {
		  if (Save_Vector[1]=="U")
		  {
			 
			  Vector="D";
			   Save_Vector[1]=Vector;
		  }
		 else if (Save_Vector[1]=="D")
		  {
			  Vector="U";
			  Save_Vector[1]=Vector;
		  }
	  }else
	  {
		  Vector="U";
		    Save_Vector[0]=e.target.cellIndex;
			 Save_Vector[1]=Vector;
	  }
	
	 
	  //alert(" "+e.target.getAttribute('data-type')+Vector);
      sortGrid(e.target.cellIndex, e.target.getAttribute('data-type'),Vector);
    };

    function sortGrid(colNum, type,Vector) {
      var tbody = grid.getElementsByTagName('tbody')[0];

      // Составить массив из TR
      var rowsArray = [].slice.call(tbody.rows);
		//alert("ra"+rowsArray);
      // определить функцию сравнения, в зависимости от типа
      var compare;

      switch (type) {
        case 'number':
          compare = function(rowA, rowB) {
			  if (Vector=="U")
			  {
            return rowA.cells[colNum].innerHTML - rowB.cells[colNum].innerHTML;
			  }else if(Vector=="D")
			  {
				    return rowB.cells[colNum].innerHTML - rowA.cells[colNum].innerHTML;
			  }
			  
          };
          break;
        case 'string':
          compare = function(rowA, rowB) {
			  if (Vector=="U")
			  {
				  	//alert("rowsA"+rowA.cells[colNum].innerHTML);
					//alert("rowsB"+rowB.cells[colNum].innerHTML);
            return rowA.cells[colNum].innerHTML > rowB.cells[colNum].innerHTML;
			  }else if(Vector=="D")
			  {
				  return rowB.cells[colNum].innerHTML > rowA.cells[colNum].innerHTML;
				  
			  }
			  
          };
          break;
		  case 'time':
		   compare = function(rowA, rowB) {
			  if (Vector=="U")
			  {
            return rowA.cells[colNum].innerHTML > rowB.cells[colNum].innerHTML;
			  }else if(Vector=="D")
			  {
				  return rowB.cells[colNum].innerHTML > rowA.cells[colNum].innerHTML;
				  
			  }
			  
          };
          break;
		   case 'date':
		   compare = function(rowA, rowB) {
			  if (Vector=="U")
			  {
            return rowA.cells[colNum].innerHTML > rowB.cells[colNum].innerHTML;
			  }else if(Vector=="D")
			  {
				  return rowB.cells[colNum].innerHTML > rowA.cells[colNum].innerHTML;
				  
			  }
			  
          };
          break;
         
      }

      // сортировать
      rowsArray.sort(compare);

      // Убрать tbody из большого DOM документа для лучшей производительности
      grid.removeChild(tbody);

      // добавить результат в нужном порядке в TBODY
      // они автоматически будут убраны со старых мест и вставлены в правильном порядке
      for (var i = 0; i < rowsArray.length; i++) {
        tbody.appendChild(rowsArray[i]);
      }

      grid.appendChild(tbody);

    }
	
