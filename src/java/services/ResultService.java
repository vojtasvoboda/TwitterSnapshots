package services;

import persistence.Edge;
import persistence.User;
import persistence.Snapshot;
import persistence.SnapshotDAO;
import persistence.Result;
import persistence.ResultDAO;
import java.util.ArrayList;
import persistence.UserDAO;

/**
 * Trida pro praci s knihovnou Gexf pro generovani Gexf XML
 * @author Bc. Vojtěch Svoboda <svobovo3@fit.cvut.cz>
 */
public class ResultService {

    private SnapshotDAO sndao = null;
    private ResultDAO rsdao = null;

    public boolean getResults() {
        sndao = new SnapshotDAO();
        rsdao = new ResultDAO();
        try {
            // rsdao.deleteAll();
            getErdosNumber();
            getDensity();
            getOverlapEmbeddedness();
            getClustering();            
            getBetweeness();            
            getReciprocity();
        } catch (Exception e) {
            System.err.println("ResultService: getResults(), chyba: " + e.toString());
            return false;
        }
        return true;
    }

    /*
     * getOverlapEmbeddedness
     */
    private void getOverlapEmbeddedness() throws Exception {
        double div, totalDiv = 0;
        int c = 0;
        int i = 0;
        double totalCommon = 0;
        double[] totalDivArray = new double[sndao.getAll().size()];
        double[] totalEmbArray = new double[sndao.getAll().size()];
        System.out.println("ResultService: getOverlapEmbeddedness, start vypoctu.");
        for (Snapshot snapshot : sndao.getAll()) {                                  //projedu vsechny snapshoty
            totalDiv = 0;
            totalCommon = 0;
            c = 0;
            if(snapshot.dejNodes().size() > 1){                                     //veme jen ty dulezity
                div = 0;
                c++;
                for(User node : snapshot.dejNodes()){                               //proejdem uzliky
                   int a = 0;
                   ArrayList<Long> connectedUsers = new ArrayList<Long>();          //sem se budou davat napojeny uzly
                   Long ID = node.getUser_id();
//                    System.out.println("----------------");
//                    System.out.println("delam usera "+ID);
                   for (Edge edge : snapshot.dejEdges()) {                          //proejdeme vsechny hrany
                        if(edge.getFromId() == ID) {                                //hledame jestli je hrana napojena na aktualne zkoumany uzel
                            if(!connectedUsers.contains(edge.getToId())){
                                connectedUsers.add(edge.getToId());                 //nasli jsme napojeny uzel
//                                System.out.println("connect "+edge.getToId());
                            }
                        }
                        if(edge.getToId() == ID) {
                            if(!connectedUsers.contains(edge.getFromId())){
                                connectedUsers.add(edge.getFromId());               //nasli jsme napojeny uzel
//                                System.out.println("connect "+edge.getFromId());
                            }
                        }
                   }
                   a = connectedUsers.size();                                       //spocitame kolik sousedu uzel ma
                   if(a > 1){                                                       //kdzy ma vic nez jednoho souseda, tzn ma smysl pro ne vubec hledat
                       int u = 0;
                       for(Long one: connectedUsers){                               //postupne pres vsechny sousedy ozkousime neighbors
                           u++;
                           int b = 0;
                           ArrayList<Long> connectedUsersB = new ArrayList<Long>();  //budeme si pamatovat sousedy souseda
                           for (Edge edge : snapshot.dejEdges()) {                   //projedeme opet vsechny hrany
                                if(edge.getFromId() == one) {                         //a hledame sosedy
                                    if(!connectedUsersB.contains(edge.getFromId())) {   //pridame souseda souseda pokud jsme ho jeste nepocitali
                                        connectedUsersB.add(edge.getFromId());
                                    }
                                    if(connectedUsers.contains(edge.getToId())){        //zvetsime pocet spolecnych
                                        b++;
                                    }
                                }
                                if(edge.getToId() == one) {                             //opet totez
                                    if(!connectedUsersB.contains(edge.getFromId())) {
                                        connectedUsersB.add(edge.getFromId());
                                    }
                                    if(connectedUsers.contains(edge.getFromId())){
                                        b++;
                                    }
                                }
                           }
                           int p = a + connectedUsersB.size();
//                           System.out.println("spojenych sousedu je "+b);
                           p = p-2-b;                                                       //odebereme uzly mezi kterymi hledame neighbour a odecteme jednou pouziti vsech spolecnych
                           System.out.println("ResultService: getOverlapEmbeddedness(), pocet sousedu "+p);
                           if ( a > 0 ) {
                               // aktualni neighbour number
                               div = b / a;
                           }
                           totalDiv = (totalDiv+div) /  u;                                  //prumerny neighbour number pro jeden node
                           totalCommon += b;
                       }
                   }else{
                       //totalDivArray.add(div);
                       //totalDivArray[i++] = 0;
                       //totalDiv
                   }
                   if ( c > 0 ) {
                        totalDiv = totalDiv / c ;                                  //prumerny clustering index
                        totalCommon = totalCommon / c;                            //prumerny embedeness
                   }
                   //totalDivArray[i++] = totalDiv;
                }
            }
            //snapshot.setNeighbour(totalDiv);                                       //tady se to priradi snapshotu
            //snapshot.setEmbedeness(totalCommon);                                       //tady se to priradi snapshotu
            totalEmbArray[i] = totalCommon;
            totalDivArray[i++] = totalDiv;

//            System.out.println("snapshot "+snapshot.getCreated());
            System.out.println("ResultService: getOverlapEmbeddedness(), embeddeness "+totalCommon);
//            System.out.println("overlap "+totalDiv);
            boolean found = false;
            for(Result result : rsdao.getAll()){
                //if(result.getCreated() == snapshot.getCreated()){
                if(result.getCreated().equals(snapshot.getCreated())){
                    result.setEmbeddedness(totalCommon);
                    result.setOverlap(totalDiv);
                    System.out.println("ResultService: getOverlapEmbeddedness, stary "+result.toString());
                    //rsdao.save(result);
                    rsdao.update(result);
                    found = true;
                }
            }
            if(!found){
                Result result = new Result(snapshot.getCreated());
                result.setEmbeddedness(totalCommon);
                result.setOverlap(totalDiv);
                System.out.println("ResultService: getOverlapEmbeddedness, novy "+result.toString());
                rsdao.save(result);
            }
        }

         for(double clust : totalDivArray){        //tahle vec nam vsude vraci nulu, asi je to dobre :) kdyz si v gephi hodime Fruchteman Reingord tak je to paradne videt
//                System.out.println("overlap "+clust);
            }

         for(double clust : totalEmbArray){        //tahle vec nam vsude vraci nulu, asi je to dobre :) kdyz si v gephi hodime Fruchteman Reingord tak je to paradne videt
//                System.out.println("embedeness "+clust);
            }
        //return totalDivArray;
    }

    /*
     * Vypocitame Density pro vsechny Snapshoty
     */
    private void getDensity() throws Exception {
        // projedu vsechny snapshoty
        for (Snapshot snapshot : sndao.getAll()) {
            // zjisti pocet uzlu
            double n = snapshot.getNodescount();
            double density = 0;
            double densityUndir = 0;
            if ( n > 1 ) {
                // spocte mozne maximum pro smerovany
                double max = n*(n-1);
                System.out.println("ResultService: getDensity(), max " + max);
                if ( max == 0.0 ) {
                    max = 1.0;
                }
                // pro nesmerovane
                double maxUndir = max / 2;
                // spocita pocet hran
                double count = snapshot.getEdgescount();
                System.out.println("ResultService: getDensity(), count " + count);
                // a zjisti density, zadna velka veda
                density = count / max;
                densityUndir = count / maxUndir;
            }

            //snapshot.setDensity(density);
            //snapshot.setDensityUndir(densityUndir);

            System.out.println("ResultService: getDensity(): density " + density);
            System.out.println("ResultService: getDensity(): densityUndir " + densityUndir);

            //System.out.println("snapshot "+snapshot.getCreated());
            boolean found = false;
            for(Result result : rsdao.getAll()){                            //najde prislusny result a ulozi to
                //if(result.getCreated() == snapshot.getCreated()){
                if(result.getCreated().equals(snapshot.getCreated())){
                    result.setDensity(density);
//                    System.out.println("stary "+result.toString());
                    //rsdao.save(result);
                    rsdao.update(result);
                    found = true;
                }
            }
            if(!found){
                Result result = new Result(snapshot.getCreated());
                result.setDensity(density);
//              System.out.println("novy "+result.toString());
                rsdao.save(result);
            }
        }

    }

            
    private void getBetweeness() throws Exception {
        double totalBetweeness = 0;
        int pocetEdge = 0;
        System.out.println("ResultService: getBetweeness() start vypoctu.");
        for (Snapshot snapshot : sndao.getAll()) {                                  //projedu vsechny snapshoty
            totalBetweeness = 0;
            if(snapshot.dejEdges().size() > 1){                                     //veme jen ty dulezity

//                System.out.println("------------------------");
//                System.out.println("snapshot "+snapshot.getCreated());
//                System.out.println("------------------------");

                for (Edge edge : snapshot.dejEdges()) {                          //proejdeme vsechny hrany
                    ArrayList<Edge> fromEdges = new ArrayList<Edge>();              //sem se budou davat hranz co jsou napojene na jeden konec te zkoumane
                    ArrayList<Edge> toEdges = new ArrayList<Edge>();                //na druhy konec zkoumane
                    long from = edge.getFromId();
                    long to = edge.getToId();
                    int fromN = 1;
                    int toN = 1;

//                    System.out.println("------------------------");
//                    System.out.println("zkoumam "+edge.getFromId()+" "+edge.getToId());
//                    System.out.println("------------------------");

                    for (Edge e : snapshot.dejEdges()) {                       //proleti hrany

                        if(((e.getFromId() == from)&&(e.getToId() != to))||((e.getToId() == from)&&(e.getFromId() != to)))      //vytahne hrany co vedou k jednomu konci
                        {
                            if(!fromEdges.contains(e)) {                                            //namlati je do listu
                                fromEdges.add(e);
//                                System.out.println("pridam hranu from "+e.getFromId()+ " to "+e.getToId());
                            }
                        }
                        
                        if(((e.getFromId() == to)&&(e.getToId() != from))||((e.getToId() == to)&&(e.getFromId() != from)))          //totez pro durhy konec hrany
                        {
                            if(!toEdges.contains(e)) {
                                toEdges.add(e);
//                                System.out.println("pridam hranu from "+e.getFromId()+ " to "+e.getToId());
                            }
                        }                        
                    }

                    int sizeFrom = 0;
                    while(sizeFrom != fromEdges.size())                 //dokud je co pripojovat
                    {
                        sizeFrom = fromEdges.size();
//                        System.out.println("mam velikost "+sizeFrom);
                        ArrayList<Edge> pomEdges = new ArrayList<Edge>();
                        for (Edge e : snapshot.dejEdges()) {            //podiva se jaky hrany jsou napojeny na uz drive napojeny hrany
                            for (Edge f : fromEdges) {
                                if((e.getFromId() == f.getFromId())||(e.getToId() == f.getFromId()) || (e.getToId() == f.getFromId()) || (e.getToId() == f.getToId())){
                                    if(!fromEdges.contains(e)) pomEdges.add(e);         //nove prida
                                }
                            }
                        }
                        for (Edge p : pomEdges){                //ted to tam opravdu prida z pomocneho listu
                            if(!fromEdges.contains(p)) {
                                fromEdges.add(p);
//                                System.out.println("pridam hranu from "+p.getFromId()+ " to "+p.getToId());
                            }
                        }
                    }

                    int sizeTo = 0;
                    while(sizeTo != fromEdges.size())               //totez pro druhy konec. je to trochu naprasene :-)
                    {
                        sizeTo = fromEdges.size();
//                        System.out.println("mam velikost "+sizeTo);
                        ArrayList<Edge> pomEdges = new ArrayList<Edge>();
                        for (Edge e : snapshot.dejEdges()) {
                            for (Edge f : toEdges) {
                                if((e.getFromId() == f.getFromId())||(e.getToId() == f.getFromId()) || (e.getToId() == f.getFromId()) || (e.getToId() == f.getToId())){
                                    if(!fromEdges.contains(e)) pomEdges.add(e);
                                }
                            }
                        }
                        for (Edge p : pomEdges){
                            if(!fromEdges.contains(p)) {
                                fromEdges.add(p);
//                                System.out.println("pridam hranu from "+p.getFromId()+ " to "+p.getToId());
                            }
                        }
                    }

                    int betweeness = sizeFrom*sizeTo;                   //vynasobi obe strany te zkoumane hrany, to co pres tuhle vede, vime ze vsechno jsou mosty
                    totalBetweeness += betweeness;                      //prida do totalni pro snap
                }
                pocetEdge = snapshot.dejEdges().size();
                if ( pocetEdge > 0 ) {
                    // zprumeruje na jednu hranu
                    totalBetweeness = totalBetweeness /  pocetEdge;
                } else {
                    totalBetweeness = 0;
                }

                System.out.println("ResultService: getBetweeness(), totalBetweeness " + totalBetweeness);

                boolean found = false;
                /// ulozime
                for(Result result : rsdao.getAll()) {
                    // najde prislusny resutl a ulozi to
                    if(result.getCreated().equals(snapshot.getCreated())){
                        result.setBetweeness(totalBetweeness);
                        rsdao.update(result);
                        found = true;
                    }
                }
                if(!found){
                    Result result = new Result(snapshot.getCreated());
                    result.setBetweeness(totalBetweeness);
                    rsdao.save(result);
                }
            }
        }
    }

    /*
     * Vypocita Reciprocity pro vsechny snapshoty
     */
    private void getReciprocity() throws Exception {
        double totalReci = 0;
        System.out.println("ResultService: getReciprocity(), start vypoctu.");
        // projdu vsechny snapshoty
        for (Snapshot snapshot : sndao.getAll()) {
            totalReci = 0;
            if(snapshot.dejEdges().size() > 1) {                                     //veme jen ty dulezity

//                System.out.println("------------------------");
//                System.out.println("snapshot "+snapshot.getCreated());
//                System.out.println("------------------------");

                for (Edge edge : snapshot.dejEdges()) {                          //proejdeme vsechny hrany                    
                    for (Edge e : snapshot.dejEdges()) {                        //hledame opacne smerovane
                        if ((e.getFromId() == edge.getToId()) && (e.getToId() == edge.getFromId())){
//                            System.out.println("obousmerna "+e.getFromId()+" to "+e.getToId());
                            totalReci++;
                        }
                    }
                }
                totalReci = totalReci / 2;              //jen jednou se pocita, ted je to brano pro oba smery
                totalReci = totalReci / snapshot.dejEdges().size();             //prumer na jednu hranu pro cely snapshot
                System.out.println("ResultService: getReciprocity(), totalReci "+totalReci);

                boolean found = false;                  ///a ulozi
                for(Result result : rsdao.getAll()){                                //najde prislusny resutl a ulozi to
                    if(result.getCreated().equals(snapshot.getCreated())){
                        result.setReciprocity(totalReci);
                        rsdao.update(result);
                        found = true;
                    }
                }
                if(!found){
                    Result result = new Result(snapshot.getCreated());
                    result.setReciprocity(totalReci);
                    rsdao.save(result);
                }

            }
        }
    }
    
    /*
     * Vypocita clustering pro vsechny snapshoty
     */
    private void getClustering() throws Exception {
        double div, totalDiv = 0;
        int c = 0;
        int i = 0;
        double[] totalDivArray = new double[sndao.getAll().size()];
        System.out.println("ResultServices: getClustering() start vypoctu.");
        for (Snapshot snapshot : sndao.getAll()) {                                  //projedu vsechny snapshoty
            totalDiv = 0;
            c = 0;
            if(snapshot.dejNodes().size() > 1){                                     //veme jen ty dulezity
                div = 0;
                c++;
                for(User node : snapshot.dejNodes()){                               //proejdem uzliky
                   int a,b = 0;
                   ArrayList<Long> connectedUsers = new ArrayList<Long>();          //sem se budou davat napojeny uzly
                   Long ID = node.getUser_id();
//                    System.out.println("----------------");
//                    System.out.println("delam usera "+ID);
                   for (Edge edge : snapshot.dejEdges()) {                          //proejdeme vsechny hrany
                        if(edge.getFromId() == ID) {                                //hledame jestli je hrana napojena na aktualne zkoumany uzel
                            if(!connectedUsers.contains(edge.getToId())){
                                connectedUsers.add(edge.getToId());                 //nasli jsme napojeny uzel
//                                System.out.println("connect "+edge.getToId());
                            }
                        }
                        if(edge.getToId() == ID) {
                            if(!connectedUsers.contains(edge.getFromId())){
                                connectedUsers.add(edge.getFromId());               //nasli jsme napojeny uzel
//                                System.out.println("connect "+edge.getFromId());
                            }
                        }
                   }
                   a = connectedUsers.size();                                       //spocitame kolik sousedu uzel ma
//                   System.out.println("pocet sousedu "+a);
                   if(a > 1){                                                       //kdzy ma vic nez jednoho souseda
                       for(Long one: connectedUsers){                               //prezkoumame vsechny sousedy
                           for (Edge edge : snapshot.dejEdges()) {                  //projedeme opet vsechny hrany
                                if(edge.getFromId() == one) {                       //a hledame jestli spolu dve sousedi
                                    if(connectedUsers.contains(edge.getToId())){
                                        b++;                                        //sousedi
                                    }
                                }
                                if(edge.getToId() == one) {
                                    if(connectedUsers.contains(edge.getFromId())){
                                        b++;                                        //sousedi
                                    }
                                }
                           }
                       }
                       // aktualni clustering index
                       System.out.println("ResultService: getClustering(), spojenych sousedu je " + b);
                       div = b / a;                   
                   } else {
                       div = 0;
                   }
                   if ( c > 0 ) {
                       // prumerny clustering index
                       totalDiv = (totalDiv+div) / c ;
                   }
                }
            }
            //snapshot.setClustering(totalDiv);                                       //tady se to priradi snapshotu

            //totalDiv = 1.5;

//            System.out.println("snapshot "+snapshot.getCreated());
            System.out.println("ResultService: getClustering(), clustering " + totalDiv);
            boolean found = false;
            for(Result result : rsdao.getAll()){                                //najde prislusny resutl a ulozi to
                //if(result.getCreated() == snapshot.getCreated()){
                if(result.getCreated().equals(snapshot.getCreated())){
                    result.setClustering(totalDiv);
                    System.out.println("ResultService: getClustering(), stary "+result.toString());
                    //rsdao.save(result);
                    rsdao.update(result);
                    found = true;
                }
            }
            if(!found){
                Result result = new Result(snapshot.getCreated());
                result.setClustering(totalDiv);
                System.out.println("ResultService: getClustering(), novy "+result.toString());
                rsdao.save(result);
            }
            totalDivArray[i++] = totalDiv;
        }
        for(double d : totalDivArray){
            System.out.println("ResultService: getClustering(), clustering " + d);
        }
    }

    /*
     * Vypocita Erdos number pro vsechny hrany
     */
    private void getErdosNumber() {
        // User stableUser = getStable();                                      //vytahne si jednoho stabilniho guru uzivatele
        // Long userID = stableUser.getUser_id();
        Long userID = new Long(50962928);
        System.out.println("ResultService: getErdosNumber(), stable id " + userID);

        ArrayList<Long> connectedUsers = new ArrayList<Long>();         //sem bude davat vsechny napojene kamarady
        connectedUsers.add(userID);
        int erdos = 0;
        int size = 0;
        boolean resultFound = false;
        
        for (Snapshot snapshot : sndao.getAll()) {

            // je nutny pocitat?
            /*
            resultFound = false;
            for(Result result : rsdao.getAll()) {
                if(result.getCreated().equals(snapshot.getCreated())){
                    System.out.println("ResultService: getErdosNumber(), result exist, continue.");
                    resultFound = true;
                    break;
                }
            }
            */

            if ( (snapshot.dejEdges().size() > 1) && (
                  !resultFound ) ) {
                int k = 0;
                while((size != connectedUsers.size())&&(k++ < 30)){
                    System.out.println("ResultService: getErdosNumber(), size "+size+" conSize "+connectedUsers.size());
                    size = connectedUsers.size();
                    erdos++;

                    ArrayList<Long> pomUsers = new ArrayList<Long>();         //sem bude davat vsechny napojene kamarady
                    for (Edge edge : snapshot.dejEdges()) {                        
                        for(Long ID : connectedUsers){
                            try {
                            System.out.println("ResultService: getErdosNumber(), from "+edge.getFromId()+" to "+edge.getToId());
                            if(edge.getFromId() == ID) {
                                if(!connectedUsers.contains(edge.getToId())){
                                    pomUsers.add(edge.getToId());
                                    System.out.println("ResultService: getErdosNumber(), connect "+edge.getToId());
                                }
                            }
                            if(edge.getToId() == ID) {
                                if(!connectedUsers.contains(edge.getFromId())){
                                    pomUsers.add(edge.getFromId());
//                                    System.out.println("connect "+edge.getFromId());
                                }
                            }
                            }catch (Exception e){
                                System.err.println("ResultService: getErdosNumber(): chyba " + e.toString());
                            }

                        }                        
                    }
                    for(Long ID : pomUsers){
                        if(!connectedUsers.contains(ID)){
                            connectedUsers.add(ID);
                        }
                    }

//                     System.out.println("-----------------------");
//                     for (Long user : connectedUsers) {
//                            System.out.println("user "+user);
//                        }
//                     System.out.println("-----------------------");
                }
            }
        }

        for (Snapshot snapshot : sndao.getAll()) {
            boolean found = false;
            // najde prislusny result a ulozi to
            for(Result result : rsdao.getAll()) {
                if(result.getCreated().equals(snapshot.getCreated())){
                    result.setErdos(erdos);
                    System.out.println("ResultService: getErdosNumber(), stary "+result.toString());
                    //rsdao.save(result);
                    rsdao.update(result);
                    found = true;
                }
            }
            if(!found){
                Result result = new Result(snapshot.getCreated());
                result.setErdos(erdos);
                System.out.println("ResultService: getErdosNumber(), novy "+result.toString());
                rsdao.save(result);
            }
        }        
        System.out.println("ResultService: getErdosNumber(), erdos " + erdos);
    }

    /**
     * projede vsechnz povedene snapshoty - aspon 1 zaznamu, a vyhledava jestli je obsazen uzitavel, hledame tak stabilniho uzivatele pro erdose cislo
     * @return
     */
    private User getStable() {

        // return
        try {
            UserDAO userDao = new UserDAO();
            User us = userDao.get("ahJmaXQtdzIwLXNzMTEtMTAxLTJyGgsSCFNuYXBzaG90GPpVDAsSBFVzZXIY0RcM");
            return us;
        } catch (Exception e) {
            System.err.println("ResultService: getStable(), chyba " + e.toString());
            return null;
        }

        /*
        int i = 0;
        List<User> stableUsers = new ArrayList<User>();
        SnapshotDAO sndao = new SnapshotDAO();
        for (Snapshot snapshot : sndao.getAll()) {                                  //projede vsechnz snapshoty
//            System.out.println("snapshot "+snapshot.dejNodes().size());
            if(snapshot.dejNodes().size() > 1) {                                     //pro povedeney snapshot
//                System.out.println("krok i "+i);
                if(i == 0) {
                    stableUsers = snapshot.dejNodes();                              //v prvnim kroce vytahne uzivatele a hodi si je do listu

//                    System.out.println("-----------------------");
//                    System.out.println("mam pocatecni uzivatele");
//                    for (User user : stableUsers) {
//                        System.out.println("user "+user.getNickname());
//                    }
//                    System.out.println("------------------------");

                } else {
                    List<User> currentUsers = snapshot.dejNodes();                   //vytahne uzivatele z aktualniho snapu do pomocneho listu

//                        System.out.println("-----------------------");
//                        System.out.println("hledame mezi");
//                        for (User user : currentUsers) {
//                            System.out.println("user "+user.getNickname());
//                        }
//                        System.out.println("------------------------");


                    for (User user : stableUsers) {                                     //projizdi puvodni list a skrtne vsechny uzivatele ktere nenajde v novem 
    //                    System.out.println("hledame usera "+user.getNickname());
                        try {
                        if(!currentUsers.contains(user)){
                            stableUsers.remove(user);
//                            System.out.println("smaznu usera "+user.getNickname());
                        }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                i++;
            }
        }

//        System.out.println("-----------------------");
//        System.out.println("projeli jsme je vsechny, vypiseme co zbyva");
//        for (User user : stableUsers) {
//            System.out.println("user "+user.getNickname()+" id "+user.getUser_id()+" key "+user.getKeyWeb());
//        }

        if(stableUsers.size() == 0) {                                                   //nenasel nikoho kdo by byl ve vsech snapshotech
            return null;
        } else {
              int[] guruArray = new int[stableUsers.size()];                            //budeme hledat guru, toho co ma jenvic hran ke kamaradum
              for (Snapshot snapshot : sndao.getAll()) {
                for (User user : stableUsers) {                                         //prodjede vsechny nalezene stabilni uzivatele
                    //System.out.println("user "+user.getNickname());
                    for (Edge edge : snapshot.dejEdges()) {                             //projede vsechny hrany a hleda v kolika hranach vystupuje
                        if ((edge.getFromId() == user.getUser_id())||(edge.getToId() == user.getUser_id())){
                            guruArray[stableUsers.indexOf(user)]++;
                        }
                        //System.out.println("from "+edge.getFromId()+" to "+edge.getToId());
                    }
                }
                // break;
              }
              int max = guruArray[0];
              int k = 0;
              User guru = stableUsers.get(0);                                               //projde pole s poctama hran k uzivatelum a najde maximalniho
              for (int j = 0; j < stableUsers.size(); j++){
                    if(guruArray[j] > max) {
                        guru = stableUsers.get(j);
                        k = j;
                    }
//                  System.out.println("j "+guruArray[j]);
              }
              return guru;
        }
         * 
         */

    }
    
}

