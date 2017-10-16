import React, {Component} from "react";

class TextInput extends Component {

  render() {
    if (this.props.visible) {
      return (
        <div>
          <span>{this.props.message}</span>
        </div>
      )
    }
    else {
      return (<div/>)
    }
  }

}

export default TextInput;