/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redis;

import redis.clients.jedis.Jedis; 

/**
 *
 * @author huulo
 */
public class redis {
    //Connecting to Redis server on localhost 
    static public Jedis Session() {
        Jedis jedis = new Jedis("localhost");

        return jedis;
    }
}
