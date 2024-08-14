package com.tinqinacademy.bff.core.conversion.converters.enums;

import com.tinqinacademy.bff.api.model.enums.BffBathroomType;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.model.enums.BathroomType;
import org.springframework.stereotype.Component;

@Component
public class BathroomTypeToBffBathroomTypeConverter extends BaseConverter<BathroomType, BffBathroomType> {

    @Override
    protected BffBathroomType convertObject(BathroomType source) {
        if (source == null) {
            return null;
        }

        return BffBathroomType.getCode(source.toString());
    }
}
