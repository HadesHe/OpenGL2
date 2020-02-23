precision mediump float;
uniform float uR;
varying vec3 vPosition;
varying vec4 vAmbient;
varying vec4 vDiffuse;
void main() {
    vec3 color;
    float n=8.0;
    float span=2.0*uR;

    int i =int((vPosition.x+uR)/span);
    int j =int((vPosition.y+uR)/span);
    int k =int((vPosition.z+uR)/span);
    int whichColor=int(mod(float(i+j+k), 2.0));
    if (whichColor==1){
        color=vec3(0.16078432, 0.99215686, 0.02745098);
    } else {
        color=vec3(1.0, 1.0, 1.0);
    }
    vec4 finalColor=vec4(color, 0);
    gl_FragColor = finalColor*vDiffuse;
}
