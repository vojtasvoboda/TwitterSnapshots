/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import javax.xml.bind.annotation.*;

/**
 *
 * @author Michal
 */
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Metric {
        @XmlAttribute
        protected double value;

        @XmlAttribute
        protected String name;        

        public Metric(){
            
        }

        public Metric(String name, double value){
            this.name = name;
            this.value = value;
        }
    }
