package com.github.dadchain.util.dadnative;

import com.github.ontio.common.Address;
import com.github.ontio.io.BinaryReader;
import com.github.ontio.io.BinaryWriter;
import com.github.ontio.io.Serializable;
import com.github.ontio.io.utils;

import java.io.IOException;

public class PlacedOrder implements Serializable {
    Address advertiser;
    String adType;
    String token;
    long bid;
    long bidBonused;
    long begin;
    long expire;
    String orderID;
    // 0: paused, 1: started, 3: closed
    long status;


    @Override
    public void deserialize(BinaryReader reader) throws IOException {
        this.advertiser = utils.readAddress(reader);
        this.adType = reader.readVarString();
        this.token = reader.readVarString();
        this.bid = utils.readVarInt(reader);
        this.bidBonused = utils.readVarInt(reader);
        this.begin = utils.readVarInt(reader);
        this.expire = utils.readVarInt(reader);
        this.orderID = reader.readVarString();
    }

    @Override
    public void serialize(BinaryWriter binaryWriter) throws IOException {
    }
}
