precision mediump float;
varying vec3 vPosition;

void main() {
    vec4 bColor=vec4(1,0,0,0);
    vec4 mColor=vec4(0.7254902,0.84313726,1,0);
    float y=vPosition.y;
    y=mod((y+100.0)*4.0,4.0);
    if(y>1.8){
        gl_FragColor=bColor;
    }else{
        gl_FragColor=mColor;
    }
}
