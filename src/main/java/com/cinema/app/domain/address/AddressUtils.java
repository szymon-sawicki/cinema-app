package com.cinema.app.domain.address;

import java.util.function.Function;

/**
 * utility class used to achieve encapsulation in Address objects
 * @author Szymon Sawicki
 */

public interface AddressUtils {

    Function<Address,Long> toId = address -> address.id;

}
