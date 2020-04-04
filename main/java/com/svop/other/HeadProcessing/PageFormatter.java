package com.svop.other.HeadProcessing;

import org.springframework.ui.Model;

import java.util.ArrayList;

public class PageFormatter {
   // Model model;
    private int size;

    public PageFormatter()
    {

    }
    public PageFormatter(int size)
    {
    this.size=size;
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

   public  void fillModel(Model model,int current)
   {
       ArrayList<Integer> pages=new ArrayList<>();
       for(int i=0;i<size;i++)
       {
           pages.add(i);
       }
       model.addAttribute("pages",pages);
       model.addAttribute("is_next","page-item");
       model.addAttribute("is_previous","page-item");
       if (current<size-1)
       {
           model.addAttribute("next",current+1);

       } else {
           model.addAttribute("is_next","page-item disabled");
           model.addAttribute("next", -1);
       }
       if (current==0)
       {
           model.addAttribute("previous",-1);
           model.addAttribute("is_previous","page-item disabled");
       } else{
           model.addAttribute("previous",current-1);
       }
   }
}
