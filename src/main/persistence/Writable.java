// Citation: This class is based on the sample persistence code provided in the
// CPSC 121 (University of British Columbia) Json Serialization Demo.
// Modifications have been made to fit the structure and requirements
// of my own application.

package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
